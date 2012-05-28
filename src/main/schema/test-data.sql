CREATE TABLE relation (
    relation_id integer,
    relation_type text,
    name text,
    route text,
    ref text,
    network text,
    operator text
);

INSERT INTO relation (relation_id,relation_type,name,route,ref,network,operator) 
VALUES (1,'type','name','route','ref','network','operator');