--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

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

--
-- Name: vt_team_seq; Type: SEQUENCE; Schema: public; Owner: current_user
--

CREATE SEQUENCE public.vt_team_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.vt_team_seq OWNER TO current_user;

--
-- Name: vt_team; Type: TABLE; Schema: public; Owner: current_user
--

CREATE TABLE public.vt_team (
                                id BIGINT NOT NULL DEFAULT NEXTVAL('public.vt_team_seq'),
                                name CHARACTER VARYING(255) NOT NULL UNIQUE
);

ALTER TABLE ONLY public.vt_team
    ADD CONSTRAINT vt_team_pkey PRIMARY KEY (id);


ALTER TABLE public.vt_team OWNER TO current_user;

--
-- Name: vt_user_seq; Type: SEQUENCE; Schema: public; Owner: current_user
--

CREATE SEQUENCE public.vt_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.vt_user_seq OWNER TO current_user;

--
-- Name: vt_user; Type: TABLE; Schema: public; Owner: current_user
--

CREATE TABLE public.vt_user (
                                id BIGINT NOT NULL DEFAULT NEXTVAL('public.vt_user_seq'),
                                email CHARACTER VARYING(255) NOT NULL UNIQUE,
                                creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                modification_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE ONLY public.vt_user
    ADD CONSTRAINT vt_user_pkey PRIMARY KEY (id);

ALTER TABLE public.vt_user OWNER TO current_user;

--
-- Name: vt_user_team; Type: TABLE; Schema: public; Owner: current_user
--

CREATE TABLE public.vt_user_team (
                                  user_id BIGINT NOT NULL,
                                  team_id BIGINT NOT NULL,
                                  PRIMARY KEY (user_id, team_id),
                                  FOREIGN KEY (user_id) REFERENCES public.vt_user(id) ON DELETE CASCADE,
                                  FOREIGN KEY (team_id) REFERENCES public.vt_team(id) ON DELETE CASCADE
);

ALTER TABLE public.vt_user_team OWNER TO current_user;

--
-- Name: vt_document_seq; Type: SEQUENCE; Schema: public; Owner: current_user
--

CREATE SEQUENCE public.vt_document_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.vt_document_seq OWNER TO current_user;

--
-- Name: vt_document; Type: TABLE; Schema: public; Owner: current_user
--

CREATE TABLE public.vt_document (
                                    id BIGINT NOT NULL DEFAULT NEXTVAL('public.vt_document_seq'),
                                    name CHARACTER VARYING(255) NOT NULL UNIQUE,
                                    content TEXT,
                                    word_count BIGINT NOT NULL DEFAULT 0,
                                    user_id BIGINT NOT NULL,
                                    creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                    modification_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE public.vt_document OWNER TO current_user;

ALTER TABLE ONLY public.vt_document
    ADD CONSTRAINT vt_document_pkey PRIMARY KEY (id);

ALTER TABLE public.vt_document
    ADD CONSTRAINT fk_document_user FOREIGN KEY (user_id)
        REFERENCES public.vt_user(id) ON DELETE CASCADE;
