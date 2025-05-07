DROP TABLE IF EXISTS men;
DROP TABLE IF EXISTS car;

CREATE TABLE car (
                     id SERIAL PRIMARY KEY,
                     brand TEXT not null ,
                     model TEXT UNIQUE,
                     price integer
);


CREATE TABLE men (
                     id serial PRIMARY KEY,
                     name TEXT UNIQUE,
                     age INT UNIQUE,
                     license BOOLEAN,
                     car_id INT,
                     FOREIGN KEY (car_id) REFERENCES car(id)
);

INSERT INTO car (brand, model, price) VALUES ('ford', 'mondeo', 1000000 );
INSERT INTO car (brand, model, price) VALUES ('lada', 'granta', 500000 );
INSERT INTO car (brand, model, price) VALUES ('ford', 'focus', 800000 );
INSERT INTO car (brand, model, price) VALUES ('toyota', 'camry', 1500000 );

INSERT INTO men (name, age, license, car_id) VALUES ('Andrey', 30, true, 1);
INSERT INTO men (name, age, license, car_id) VALUES ('Sergey', 35, true, 2);
INSERT INTO men (name, age, license, car_id) VALUES ('Victor', 40, true, 3);
INSERT INTO men (name, age, license, car_id) VALUES ('Alex', 45, true, 4);
INSERT INTO men (name, age, license, car_id) VALUES ('Ivan', 50, true, 1);


select men.name as men_name, men.age as men_age, men.license as license,
       car.brand as type, car.model as model, car.price as price
from men inner join car on men.car_id = car.id;
