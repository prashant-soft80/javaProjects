package example.client;

import com.mongodb.MongoCredential;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.concurrent.TimeUnit;

import static example.util.AssertionUtils.notNull;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class MongoClientConfiguration {

    private final String name;
    private final String hostNodes;
    private final String user;
    private final String password;
    private final String authenticationDatabaseName;
    private final String authenticationMechanism;
    private final int poolSize;
    private final long readTimeout;
    private final String readConcern;
    private final String readPreference;
    private final String writeConcern;
    private final boolean enableJournal;
    private final long writeTimeout;
    private final long connectionTimeWait;
    private final long minEvictionTimeWait;
    private final long maxStalenessSeconds;

    private final long queryLogThresholdDuration;
    private final CodecRegistry codecRegistry;

    private MongoClientConfiguration(Builder builder) {
        name = notNull(builder.getName(), "name");
        hostNodes = notNull(builder.getHostNodes(), "hostNodes");
        authenticationDatabaseName = builder.getAuthenticationDatabaseName();
        authenticationMechanism = builder.getAuthenticationMechanism();
        user = builder.getUser();
        password = builder.getPassword();
        poolSize = builder.getPoolSize();
        connectionTimeWait = builder.getConnectionTimeWait();
        readTimeout = builder.getReadTimeoutMillis();
        readConcern = builder.getReadConcern();
        readPreference = builder.getReadPreference();
        writeConcern = builder.getWriteConcern();
        enableJournal = builder.isEnableJournal();
        writeTimeout = builder.getWriteTimeout();
        minEvictionTimeWait = builder.getMinEvictionTimeWait();

        queryLogThresholdDuration = builder.getQueryLogThresholdDuration();
        codecRegistry = builder.getCodecRegistry();
        maxStalenessSeconds = builder.getMaxStalenessSeconds();

        if (!StringUtils.isBlank(user)) {
            notNull(builder.getPassword(), "password");
            notNull(builder.getAuthenticationDatabaseName(), "authenticationDatabaseName");
        }
    }

    public long getQueryLogThresholdDuration() {
        return queryLogThresholdDuration;
    }

    public String getName() {
        return name;
    }

    public String getHostNodes() {
        return hostNodes;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthenticationDatabaseName() {
        return authenticationDatabaseName;
    }

    public String getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    public boolean isEnableJournal() {
        return enableJournal;
    }

    public long getMaxStalenessSeconds() {
        return maxStalenessSeconds;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public String getReadConcern() {
        return readConcern;
    }

    public String getReadPreference() {
        return readPreference;
    }

    public String getWriteConcern() {
        return writeConcern;
    }


    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getConnectionTimeWait() {
        return connectionTimeWait;
    }

    public long getMinEvictionTimeWait() {
        return minEvictionTimeWait;
    }


    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "MongoClientConfiguration{" +
                "name='" + name + '\'' +
                ", hostNodes='" + hostNodes + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", authenticationDatabaseName='" + authenticationDatabaseName + '\'' +
                ", authenticationMechanism='" + authenticationMechanism + '\'' +
                ", poolSize=" + poolSize +
                ", readTimeout=" + readTimeout +
                ", readConcern='" + readConcern + '\'' +
                ", readPreference='" + readPreference + '\'' +
                ", writeConcern='" + writeConcern + '\'' +
                ", enableJournal=" + enableJournal +
                ", writeTimeout=" + writeTimeout +
                ", connectionTimeWait=" + connectionTimeWait +
                ", minEvictionTimeWait=" + minEvictionTimeWait +
                ", maxStalenessSeconds=" + maxStalenessSeconds +
                ", queryLogThresholdDuration=" + queryLogThresholdDuration +
                ", codecRegistry=" + codecRegistry +
                '}';
    }

    public static class Builder {

        private String name;
        private String hostNodes;
        private String user;
        private String password;
        private String authenticationDatabaseName;
        private int poolSize = 20;
        private String readConcern = "DEFAULT";
        private String readPreference = "PRIMARY";
        private String writeConcern = "ACKNOWLEDGED";

        private boolean enableJournal;
        private long writeTimeout = TimeUnit.MINUTES.toMillis(5);
        private long minEvictionTimeWait = TimeUnit.MINUTES.toMillis(15);
        private long connectionTimeWait = TimeUnit.SECONDS.toMillis(10);
        private long readTimeoutMillis = connectionTimeWait * 2;
        private long queryLogThresholdDuration = TimeUnit.MINUTES.toMillis(30);

        private CodecRegistry codecRegistry;
        private long maxStalenessSeconds = -1; //no maximum staleness
        private String authenticationMechanism = MongoCredential.SCRAM_SHA_1_MECHANISM;

        public Builder maxStalenessSeconds(long maxStalenessSeconds) {
            this.maxStalenessSeconds = maxStalenessSeconds;
            return this;
        }

        public Builder readConcern(String readConcern) {
            this.readConcern = readConcern;
            return this;
        }

        public Builder readPreference(String readPreference) {
            this.readPreference = readPreference;
            return this;
        }

        public Builder writeConcern(String writeConcern) {
            this.writeConcern = writeConcern;
            return this;
        }

        public Builder enableJournal(boolean enableJournal) {
            this.enableJournal = enableJournal;
            return this;
        }

        public Builder writeTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder minEvictionTimeWait(long minEvictionTimeWait) {
            this.minEvictionTimeWait = minEvictionTimeWait;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder hostNodes(String hostNodes) {
            this.hostNodes = hostNodes;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder authenticationDatabaseName(String authenticationDatabaseName) {
            this.authenticationDatabaseName = authenticationDatabaseName;
            return this;
        }

        public Builder poolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public Builder connectionTimeWait(long connectionTimeWait) {
            this.connectionTimeWait = connectionTimeWait;
            return this;
        }

        public Builder readTimeoutMillis(long readTimeoutMillis) {
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        public Builder queryLogThresholdDuration(long queryLogThresholdDuration) {
            this.queryLogThresholdDuration = queryLogThresholdDuration;
            return this;
        }

        public Builder codecRegistry(CodecRegistry codecRegistry) {
            this.codecRegistry = codecRegistry;
            return this;
        }

        public Builder authenticationMechanism(String authenticationMechanism) {
            this.authenticationMechanism = authenticationMechanism;
            return this;
        }

        public MongoClientConfiguration build() {
            return new MongoClientConfiguration(this);
        }

        public String getName() {
            return name;
        }

        public String getHostNodes() {
            return hostNodes;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public String getAuthenticationDatabaseName() {
            return authenticationDatabaseName;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public String getReadConcern() {
            return readConcern;
        }

        public String getReadPreference() {
            return readPreference;
        }

        public String getWriteConcern() {
            return writeConcern;
        }

        public boolean isEnableJournal() {
            return enableJournal;
        }

        public long getWriteTimeout() {
            return writeTimeout;
        }

        public long getMinEvictionTimeWait() {
            return minEvictionTimeWait;
        }

        public long getConnectionTimeWait() {
            return connectionTimeWait;
        }

        public long getReadTimeoutMillis() {
            return readTimeoutMillis;
        }

        public long getQueryLogThresholdDuration() {
            return queryLogThresholdDuration;
        }

        public CodecRegistry getCodecRegistry() {
            return codecRegistry;
        }

        public String getAuthenticationMechanism() {
            return authenticationMechanism;
        }


        public long getMaxStalenessSeconds() {
            return maxStalenessSeconds;
        }
    }

}
