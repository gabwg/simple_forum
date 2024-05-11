SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
SET search_path TO lmsdb,public;
SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE accounts (
    user_id      serial    primary key,
    username character varying(50),
    password character varying(50),
    email character varying(50)
);

