db = new Mongo().getDB("fiazard");
testdb = new Mongo().getDB("fiazard-test");

/* Products (also contain Categories) */
db.createCollection('products');
testdb.createCollection('products');

/* Openinghours */
db.createCollection('openinghours');
testdb.createCollection('openinghours');

/* Buns */
db.createCollection('buns');
testdb.createCollection('buns');

/* Toppings */
db.createCollection('toppings');
testdb.createCollection('toppings');

/* Condiments */
db.createCollection('condiments');
testdb.createCollection('condiments');