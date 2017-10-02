package example.client;

import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version.Main;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;
import de.flapdoodle.embed.process.store.IArtifactStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class EmbeddedMongoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedMongoServer.class);
    private static final IArtifactStore ARTIFACT_STORE;
    private static final IRuntimeConfig RUNTIME_CONFIG;
    private static final MongodStarter MONGOD_STARTER;
    private MongodExecutable mongodExec;
    private MongodProcess mongodProcess;
    private final AtomicBoolean running = new AtomicBoolean();
    private ServerAddress serverAddress;
    private AtomicReference<MongoClientOptions> options = new AtomicReference(MongoClientOptions.builder().build());
    private final Supplier<MongoClient> lazyClient = Suppliers.memoize(() -> new MongoClient(this.serverAddress, this.options.get()));

    public EmbeddedMongoServer() {
    }

    public void start() throws Exception {
        if (this.running.compareAndSet(false, true)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            String port = System.getProperty("embedded.mongo.port");
            int serverPort = port == null ? Network.getFreeServerPort() : Integer.parseInt(port);
            IMongodConfig mongodConfig = (new MongodConfigBuilder()).version(Main.PRODUCTION).net(new Net(serverPort, Network.localhostIsIPv6())).build();
            this.mongodExec = MONGOD_STARTER.prepare(mongodConfig);
            this.mongodProcess = this.mongodExec.start();
            this.serverAddress = new ServerAddress(InetAddress.getLocalHost().getCanonicalHostName(), serverPort);
            LOG.info("Started {}:{} in {} ", new Object[]{this.serverAddress.getHost(), Integer.valueOf(this.serverAddress.getPort()), stopwatch.stop()});
        }

    }

    public ServerAddress serverAddress() {
        if (this.running.get()) {
            return this.serverAddress;
        } else {
            throw new IllegalStateException("Server not initialized");
        }
    }

    public MongoClient getMongoClient(MongoClientOptions cfg) {
        if (this.running.get()) {
            this.options.set(cfg);
            return this.lazyClient.get();
        } else {
            throw new IllegalStateException("EmbeddedMongoServer is not running");
        }
    }

    public void shutdown() {
        if (this.running.compareAndSet(true, false)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            this.lazyClient.get().close();
            this.mongodProcess.stop();
            this.mongodExec.stop();
            LOG.info("Stopped {}:{} in {} ", new Object[]{this.serverAddress.getHost(), Integer.valueOf(this.serverAddress.getPort()), stopwatch.stop()});
        }

    }

    public static void main(String[] args) throws Exception {
        EmbeddedMongoServer embeddedMongoServer = new EmbeddedMongoServer();
        embeddedMongoServer.start();
        System.out.println("********************************************************************");
        Thread.sleep(10000L);
        embeddedMongoServer.shutdown();
    }

    static {
        ARTIFACT_STORE = (new ExtractedArtifactStoreBuilder()).defaults(Command.MongoD).downloader(new MongoExecutableDownloader()).build();
        RUNTIME_CONFIG = (new RuntimeConfigBuilder()).defaultsWithLogger(Command.MongoD, LOG).artifactStore(ARTIFACT_STORE).build();
        MONGOD_STARTER = MongodStarter.getInstance(RUNTIME_CONFIG);
    }
}
