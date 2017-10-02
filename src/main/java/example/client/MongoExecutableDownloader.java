package example.client;

import com.jcraft.jsch.*;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.io.directories.PropertyOrPlatformTempDir;
import de.flapdoodle.embed.process.io.file.Files;
import de.flapdoodle.embed.process.io.progress.IProgressListener;
import de.flapdoodle.embed.process.store.IDownloader;

import java.io.*;
import java.util.Optional;

/**
 * @author prashant.singh
 * @since 02/10/2017
 */
class MongoExecutableDownloader implements IDownloader {
    private static final int BUFFER_LENGTH = 1024 * 8 * 8;
    private static final int READ_COUNT_MULTIPLIER = 100;


    private final String username = "miodeploy";
    private final String password = "ensure";
    private final String host = "localhost";
    private final int port = 22;
    private final String srcDir = "mongodb-executables";


    @Override
    public String getDownloadUrl(IDownloadConfig runtime, Distribution distribution) {
        return runtime.getDownloadPath().getPath(distribution) + runtime.getPackageResolver().getPath(distribution);
    }

    @Override
    public File download(IDownloadConfig downloadConfig, Distribution distribution) throws IOException {
        String progressLabel = "Download " + distribution;
        IProgressListener progress = downloadConfig.getProgressListener();
        progress.start(progressLabel);

        File destFile = Files.createTempFile(PropertyOrPlatformTempDir.defaultInstance(), downloadConfig.getFileNaming()
                .nameFor(downloadConfig.getDownloadPrefix(), "." + downloadConfig.getPackageResolver().getArchiveType(distribution)));
        final String srcFile = downloadConfig.getPackageResolver().getPath(distribution);
        if (destFile.canWrite()) {
            try {
                download(progress, progressLabel, srcFile, destFile);
            } catch (Exception e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Can not write " + destFile);
        }

        progress.done(progressLabel);
        return destFile;
    }


    private void download(IProgressListener progress, String progressLabel, String srcFile, File destDir) throws Exception {
        progress.info(progressLabel, "Downloading from sftp " + username + '@' + host + ':' + port + '/' + srcDir + '/' + srcFile);
        ChannelSftp channel = null;
        Session session = null;
        try {
            final JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            final UserInfo ui = new UserInfoImpl();
            session.setUserInfo(ui);
            session.connect();
            progress.info(progressLabel, "Connected");
            if (session.isConnected()) {
                final Channel ch = session.openChannel("sftp");
                if (ch instanceof ChannelSftp) {
                    channel = (ChannelSftp) ch;
                    channel.connect();
                    channel.cd(srcDir);
                    long length = channel.lstat(srcFile).getSize();
                    progress.info(progressLabel, "DownloadSize: " + length);
                    long downloadStartedAt = System.currentTimeMillis();
                    try (BufferedInputStream bis = new BufferedInputStream(channel.get(srcFile));
                         BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destDir))) {

                        byte[] buf = new byte[BUFFER_LENGTH];
                        int read;
                        long readCount = 0;
                        while ((read = bis.read(buf)) != -1) {
                            bos.write(buf, 0, read);
                            readCount += read;
                            if (readCount > length) {
                                length = readCount;
                            }

                            progress.progress(progressLabel, (int) (readCount * READ_COUNT_MULTIPLIER / length));
                        }
                        progress.info(progressLabel, "downloaded with " + downloadSpeed(downloadStartedAt, length));
                    }
                }
            }
        } finally {
            Optional.ofNullable(channel).ifPresent(ChannelSftp::exit);
            Optional.ofNullable(session).ifPresent(Session::disconnect);
        }
    }


    private String downloadSpeed(long downloadStartedAt, long downloadSize) {
        long timeUsed = (System.currentTimeMillis() - downloadStartedAt) / 1000;
        if (timeUsed == 0) {
            timeUsed = 1;
        }
        long kbPerSecond = downloadSize / (timeUsed * 1024);
        return kbPerSecond + "kb/s";
    }

    private class UserInfoImpl implements UserInfo, UIKeyboardInteractive {

        @Override
        public String[] promptKeyboardInteractive(final String destination, final String name, final String instruction, final String[] prompt, final boolean[] echo) {
            return new String[]{password};
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public boolean promptPassword(final String message) {
            return true;
        }

        @Override
        public boolean promptPassphrase(final String message) {
            return false;
        }

        @Override
        public boolean promptYesNo(final String message) {
            return true;
        }

        @Override
        public void showMessage(final String message) {
            //logger.info("message: " + message);
        }
    }

}
