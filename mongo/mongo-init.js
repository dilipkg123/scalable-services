db.auth('root', 'pass123')

db = db.getSiblingDB('product-service')

db.createUser(
        {
            user: "root",
            pwd: "pass123",
            roles: [
                {
                    role: "userAdminAnyDatabase",
                    db: "product-service"
                },
               "readWriteAnyDatabase"
            ]
        }
);
