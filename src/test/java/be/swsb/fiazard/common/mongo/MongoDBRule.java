package be.swsb.fiazard.common.mongo;


import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.mongojack.JacksonDBCollection;
import org.mongojack.MongoCollection;

import com.commercehub.dropwizard.mongo.MongoClientFactory;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClientURI;

public class MongoDBRule extends TestWatcher {

    private static Logger logger = Logger.getLogger(MongoDBRule.class.getName());

    private DB db;
    private List<String> mongoDBReservedColls = Lists.newArrayList("system.indexes");

    public static MongoDBRule create() {
        try {
            return new MongoDBRule();
        } catch (UnknownHostException e) {
            throw new RuntimeException("shit went wrong!", e);
        }
    }

    private MongoDBRule() throws UnknownHostException {
        MongoClientFactory mongoClientFactory = new MongoClientFactory();
        String dbName = "fiazard-test";
        mongoClientFactory.setDbName(dbName);
        mongoClientFactory.setUri(new MongoClientURI("mongodb://localhost:27017/?maxPoolSize=50&maxIdleTimeMS=300000"));
        db = mongoClientFactory.build().getDB(dbName);
    }

    public DB getDB() {
        return db;
    }

    public <T> void persist(T persistableObject) {
		collectionFor(persistableObject).save(persistableObject);
	}

	@SuppressWarnings("unchecked")
	private <T> JacksonDBCollection<T, String> collectionFor(T persistableObject) {
		return (JacksonDBCollection<T, String>) JacksonDBCollection.wrap(db.getCollection(collectionNameFor(persistableObject)), persistableObject.getClass(), String.class);
	}
	
	private String collectionNameFor(Object obj) {
		return obj.getClass().getAnnotation(MongoCollection.class).name();
	}

	@Override
    protected void starting(Description description) {
        super.starting(description);
        emptyAllMongoCollections();
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
//        emptyAllMongoCollections();
    }

    private void emptyAllMongoCollections() {
        db.getCollectionNames()
                .stream()
                .filter(collName -> !mongoDBReservedColls.contains(collName))
                .forEach(collName -> {
                    recreate(collName);
//                    logger.log(Level.INFO, "deleted {0} docs of collection {1}", new Object[]{result.getN(), collName});
                });
    }

    private void recreate(String collName) {
        logger.log(Level.INFO, "attempting recreation of {0}", collName);
        db.getCollection(collName).drop();
        db.createCollection(collName, new BasicDBObject("capped", true).append("size", 1048576));
    }
}