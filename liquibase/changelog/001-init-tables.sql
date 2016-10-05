--liquibase formatted sql

--changeset sleadbeater:001-init

CREATE TABLE "Users"
(
  "Id" serial NOT NULL,
  "UserName" character varying(64),
  "FirstName" character varying(128),
  "LastName" character varying(128),
  "Password" character varying(128),
  CONSTRAINT "Users_Id" PRIMARY KEY ("Id"),
  CONSTRAINT "Users_UserName" UNIQUE ("UserName")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Users"
  OWNER TO application_admin;

-- rollback DROP TABLE "Users";

