
ALTER TABLE genres DROP COLUMN banned;
ALTER TABLE genres DROP COLUMN favorite;

CREATE TABLE preferences(
preferences_id uuid PRIMARY KEY,
name uuid UNIQUE NOT NULL REFERENCES genres(genres_id),
user_name uuid NOT NULL REFERENCES users(users_id),
banned BOOLEAN NOT NULL,
favorite BOOLEAN NOT NULL
);

INSERT INTO genres(genres_id, name)
VALUES      ('897f3c13-3f23-4cbe-b4c7-551767a58e92','Historia');

INSERT INTO genres(genres_id, name)
VALUES      ('33bcc138-c33f-4faf-9a72-745288e8d07a','Vida');

INSERT INTO genres(genres_id, name)
VALUES      ('2dadcb13-1859-4fcf-95a9-0b2cf105f51b','Videojuegos');

INSERT INTO genres(genres_id, name)
VALUES      ('699122f9-28ca-4c85-9ea8-bf4944dc68a6','Random');

INSERT INTO curious_data(curious_data_id, genre, curiosity)
VALUES  (gen_random_uuid(), '897f3c13-3f23-4cbe-b4c7-551767a58e92', 'En 1972, Nolan Bushnell creó Atari, dandonos clásicos como Pong. Cuatro años después la vendió e inauguró Chuck E. Cheese,donde tienen una copia llamada *Ping* debido a que no podían usar el nombre original por copyright.'),
        (gen_random_uuid(), '897f3c13-3f23-4cbe-b4c7-551767a58e92', 'Mas o menos en el año 1870, en la ciudad de Liège, en Bélgica, se trató de entrenar a 37 gatos como mensajeros, llevando mensajes en bolsitas impermeables alrededor del cuello. El gato que menos tardaba en entregar el mensaje tardaba 5 horas, y los otros podían tardar dias, por lo que la idea no duró demasiado.'),
        (gen_random_uuid(), '897f3c13-3f23-4cbe-b4c7-551767a58e92', 'El mito del monstruo del Lago Ness se remonta hasta includo el año 500 dC, del cual data un documento escrito por un monje irlandes hablando de una bestia acuatica que esta acabando con los pescadores locales'),
        (gen_random_uuid(), '33bcc138-c33f-4faf-9a72-745288e8d07a', 'El arbusto Gimpy-gimpy posee unas microagujas en sus hojas con una neurotoxina tan potente que tanto gente como animales se vuelven locos o se quitan la vida del dolor que provoca. Este dolor perdura durante bastante tiempo y estas microagujas estan incluso en hojas muertas de la planta.'),
        (gen_random_uuid(), '33bcc138-c33f-4faf-9a72-745288e8d07a', 'EL ave lira pueden imitar casi a la perfección todo sonido que escuchan, con el perfecto ejemplo de Chook, un ejemplar del zoo australiano que podía imitar perfectamente herramientas de construcción o incluso una motosierra en marcha'),
        (gen_random_uuid(), '33bcc138-c33f-4faf-9a72-745288e8d07a', 'Napoleón Bonaparte una vez fue atacado por una manada de conejos. Estos fueron soltados para una partida de caza en celebración de una conquista reciente, pero en vez de huir, estos se dieron la vuelta y atacaron a todos los participantes.'),
        (gen_random_uuid(), '2dadcb13-1859-4fcf-95a9-0b2cf105f51b', 'La saga de terror Corpse Party comenzó como una participación para una Game Jam usando RPG Maker 98 en 1996. Logró el segundo puesto.'),
        (gen_random_uuid(), '2dadcb13-1859-4fcf-95a9-0b2cf105f51b', 'El peculiar juego de PSX LSD Emulator es considerada una de las obras experimentales para peculiares respecto a videojuegos, su contenido aparentemente aleatorio esta basados en el diario de sueños de uno de los empleados de la compañía creadora.'),
        (gen_random_uuid(), '699122f9-28ca-4c85-9ea8-bf4944dc68a6', 'El aliento de mi gato huele a comida de gato.'),
        (gen_random_uuid(), '699122f9-28ca-4c85-9ea8-bf4944dc68a6', 'Otto es Otto escrito al revés.'),
        (gen_random_uuid(), '699122f9-28ca-4c85-9ea8-bf4944dc68a6', 'TODO SALE A PEDIR DE SCALA.');