--
-- PostgreSQL database cluster dump
--

-- Started on 2025-08-31 04:08:19

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:WTjUKWPzASxJrctCH4FcNg==$WkR+QIAtLBlerhp3v0jZetQ73jw8tGS8OUgPelZjtuQ=:n/+W3Z9xxChlBtheSLBkQBxe2JNna4mLtUVHxugHRPE=';
CREATE ROLE tpgr63eventosuyadmin;
ALTER ROLE tpgr63eventosuyadmin WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:cKYxucYb6qoS7jTuYO372w==$qavlnN4Jk/8RGsSkl/ra/PyGus7f8JzAkWB5SK7VV58=:0+kDLrYcKb/69FXyR0WpAA8xnj7yYYpGIFOfCgmjdCI=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.10 (Debian 16.10-1.pgdg13+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-08-31 04:08:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2025-08-31 04:08:19

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.10 (Debian 16.10-1.pgdg13+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-08-31 04:08:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2025-08-31 04:08:19

--
-- PostgreSQL database dump complete
--

--
-- Database "tpgr63eventosuy" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.10 (Debian 16.10-1.pgdg13+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-08-31 04:08:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3492 (class 1262 OID 16385)
-- Name: tpgr63eventosuy; Type: DATABASE; Schema: -; Owner: tpgr63eventosuyadmin
--

CREATE DATABASE tpgr63eventosuy WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE tpgr63eventosuy OWNER TO tpgr63eventosuyadmin;

\connect tpgr63eventosuy

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16390)
-- Name: categoria; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.categoria (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    nombre character varying(120) NOT NULL
);


ALTER TABLE public.categoria OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 216 (class 1259 OID 16395)
-- Name: edicion; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.edicion (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    ciudad character varying(120) NOT NULL,
    fechaalta date NOT NULL,
    fechafin date NOT NULL,
    fechainicio date NOT NULL,
    nombre character varying(120) NOT NULL,
    pais character varying(120) NOT NULL,
    sigla character varying(20) NOT NULL,
    evento_id bigint NOT NULL,
    organizador_id bigint NOT NULL
);


ALTER TABLE public.edicion OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 217 (class 1259 OID 16400)
-- Name: evento; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.evento (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    descripcion character varying(300),
    fecha_alta date NOT NULL,
    nombre character varying(120) NOT NULL,
    sigla character varying(20) NOT NULL
);


ALTER TABLE public.evento OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 218 (class 1259 OID 16405)
-- Name: evento_categoria; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.evento_categoria (
    evento_id bigint NOT NULL,
    categoria_id bigint NOT NULL
);


ALTER TABLE public.evento_categoria OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 222 (class 1259 OID 16440)
-- Name: global_seq; Type: SEQUENCE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE SEQUENCE public.global_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.global_seq OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 223 (class 1259 OID 24733)
-- Name: institucion; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.institucion (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    descripcion character varying(300) NOT NULL,
    nombre character varying(120) NOT NULL,
    sitioweb character varying(120)
);


ALTER TABLE public.institucion OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 219 (class 1259 OID 16410)
-- Name: registro; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.registro (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    costo real NOT NULL,
    fecha timestamp(6) without time zone NOT NULL,
    asistente_id bigint NOT NULL,
    edicion_id bigint NOT NULL,
    tipo_registro_id bigint NOT NULL
);


ALTER TABLE public.registro OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 220 (class 1259 OID 16415)
-- Name: tipo_registro; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.tipo_registro (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    costo real NOT NULL,
    cupo integer NOT NULL,
    descripcion character varying(300) NOT NULL,
    nombre character varying(120) NOT NULL,
    edicion_id bigint NOT NULL
);


ALTER TABLE public.tipo_registro OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 221 (class 1259 OID 16420)
-- Name: usuario; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.usuario (
    tipo character varying(31) NOT NULL,
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    apellido character varying(120) NOT NULL,
    correo character varying(180) NOT NULL,
    nickname character varying(40) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(300) NOT NULL,
    sitioweb character varying(120),
    fechanacimiento date NOT NULL,
    institucion_id bigint NOT NULL
);


ALTER TABLE public.usuario OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 3478 (class 0 OID 16390)
-- Dependencies: 215
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.categoria (id, created_at, updated_at, version, nombre) FROM stdin;
1	2024-01-27 00:00:01	2024-01-27 00:00:00	1	Seminario
2	2024-01-27 00:00:01	2024-01-27 00:00:01	1	Taller
\.


--
-- TOC entry 3479 (class 0 OID 16395)
-- Dependencies: 216
-- Data for Name: edicion; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.edicion (id, created_at, updated_at, version, ciudad, fechaalta, fechafin, fechainicio, nombre, pais, sigla, evento_id, organizador_id) FROM stdin;
\.


--
-- TOC entry 3480 (class 0 OID 16400)
-- Dependencies: 217
-- Data for Name: evento; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.evento (id, created_at, updated_at, version, descripcion, fecha_alta, nombre, sigla) FROM stdin;
1	2025-08-30 01:03:58.034233	2025-08-30 01:03:58.034233	0	Una buena descripción	2025-08-30	Evento1	E1
2	2025-08-30 01:20:21.261345	2025-08-30 01:20:21.261345	0	asdsdsad	2025-08-22	Evento2	E2
3	2025-08-30 02:31:02.308289	2025-08-30 02:31:02.308289	0		2025-08-30	Evento 3	E3
4	2025-08-30 19:00:22.574808	2025-08-30 19:00:22.574808	0	Descripción 2	2025-08-30	Evento 2	E2
6	2025-08-31 01:31:39.139938	2025-08-31 01:31:39.139938	0	asadasd	2025-08-31	Evento 1	Evento 1
\.


--
-- TOC entry 3481 (class 0 OID 16405)
-- Dependencies: 218
-- Data for Name: evento_categoria; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.evento_categoria (evento_id, categoria_id) FROM stdin;
3	1
4	1
4	2
6	1
\.


--
-- TOC entry 3486 (class 0 OID 24733)
-- Dependencies: 223
-- Data for Name: institucion; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.institucion (id, created_at, updated_at, version, descripcion, nombre, sitioweb) FROM stdin;
\.


--
-- TOC entry 3482 (class 0 OID 16410)
-- Dependencies: 219
-- Data for Name: registro; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.registro (id, created_at, updated_at, version, costo, fecha, asistente_id, edicion_id, tipo_registro_id) FROM stdin;
\.


--
-- TOC entry 3483 (class 0 OID 16415)
-- Dependencies: 220
-- Data for Name: tipo_registro; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.tipo_registro (id, created_at, updated_at, version, costo, cupo, descripcion, nombre, edicion_id) FROM stdin;
\.


--
-- TOC entry 3484 (class 0 OID 16420)
-- Dependencies: 221
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.usuario (tipo, id, created_at, updated_at, version, apellido, correo, nickname, nombre, descripcion, sitioweb, fechanacimiento, institucion_id) FROM stdin;
\.


--
-- TOC entry 3494 (class 0 OID 0)
-- Dependencies: 222
-- Name: global_seq; Type: SEQUENCE SET; Schema: public; Owner: tpgr63eventosuyadmin
--

SELECT pg_catalog.setval('public.global_seq', 6, true);


--
-- TOC entry 3296 (class 2606 OID 16394)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 3300 (class 2606 OID 16399)
-- Name: edicion edicion_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT edicion_pkey PRIMARY KEY (id);


--
-- TOC entry 3308 (class 2606 OID 16409)
-- Name: evento_categoria evento_categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT evento_categoria_pkey PRIMARY KEY (evento_id, categoria_id);


--
-- TOC entry 3304 (class 2606 OID 16404)
-- Name: evento evento_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- TOC entry 3323 (class 2606 OID 24739)
-- Name: institucion institucion_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id);


--
-- TOC entry 3310 (class 2606 OID 16414)
-- Name: registro registro_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT registro_pkey PRIMARY KEY (id);


--
-- TOC entry 3314 (class 2606 OID 16419)
-- Name: tipo_registro tipo_registro_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT tipo_registro_pkey PRIMARY KEY (id);


--
-- TOC entry 3298 (class 2606 OID 16428)
-- Name: categoria uk_categoria_nombre; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT uk_categoria_nombre UNIQUE (nombre);


--
-- TOC entry 3302 (class 2606 OID 16430)
-- Name: edicion uk_edicion_nombre_evento; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT uk_edicion_nombre_evento UNIQUE (nombre, evento_id);


--
-- TOC entry 3306 (class 2606 OID 16432)
-- Name: evento uk_evento_nombre; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT uk_evento_nombre UNIQUE (nombre);


--
-- TOC entry 3325 (class 2606 OID 24741)
-- Name: institucion uk_institucion_nombre; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.institucion
    ADD CONSTRAINT uk_institucion_nombre UNIQUE (nombre);


--
-- TOC entry 3312 (class 2606 OID 16434)
-- Name: registro uk_registro_asistente_edicion; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT uk_registro_asistente_edicion UNIQUE (asistente_id, edicion_id);


--
-- TOC entry 3316 (class 2606 OID 16436)
-- Name: tipo_registro uk_tiporeg_nombre_edicion; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT uk_tiporeg_nombre_edicion UNIQUE (nombre, edicion_id);


--
-- TOC entry 3319 (class 2606 OID 16439)
-- Name: usuario uklbkxel95iw6vtu2w6huyrpu26; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uklbkxel95iw6vtu2w6huyrpu26 UNIQUE (nickname);


--
-- TOC entry 3321 (class 2606 OID 16426)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3317 (class 1259 OID 16437)
-- Name: ix_usuario_nickname; Type: INDEX; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE INDEX ix_usuario_nickname ON public.usuario USING btree (nickname);


--
-- TOC entry 3334 (class 2606 OID 24742)
-- Name: usuario fk_asistente_institucion; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_asistente_institucion FOREIGN KEY (institucion_id) REFERENCES public.institucion(id);


--
-- TOC entry 3326 (class 2606 OID 16441)
-- Name: edicion fk_edicion_evento; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT fk_edicion_evento FOREIGN KEY (evento_id) REFERENCES public.evento(id);


--
-- TOC entry 3327 (class 2606 OID 24708)
-- Name: edicion fk_edicion_organizador; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT fk_edicion_organizador FOREIGN KEY (organizador_id) REFERENCES public.usuario(id);


--
-- TOC entry 3328 (class 2606 OID 16446)
-- Name: evento_categoria fk_ev_cat_categoria; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT fk_ev_cat_categoria FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 3329 (class 2606 OID 16451)
-- Name: evento_categoria fk_ev_cat_evento; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT fk_ev_cat_evento FOREIGN KEY (evento_id) REFERENCES public.evento(id);


--
-- TOC entry 3330 (class 2606 OID 16456)
-- Name: registro fk_reg_asistente; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_asistente FOREIGN KEY (asistente_id) REFERENCES public.usuario(id);


--
-- TOC entry 3331 (class 2606 OID 16461)
-- Name: registro fk_reg_edicion; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_edicion FOREIGN KEY (edicion_id) REFERENCES public.edicion(id);


--
-- TOC entry 3332 (class 2606 OID 16466)
-- Name: registro fk_reg_tiporeg; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_tiporeg FOREIGN KEY (tipo_registro_id) REFERENCES public.tipo_registro(id);


--
-- TOC entry 3333 (class 2606 OID 16471)
-- Name: tipo_registro fk_tiporeg_edicion; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT fk_tiporeg_edicion FOREIGN KEY (edicion_id) REFERENCES public.edicion(id);


--
-- TOC entry 3493 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO tpgr63eventosuyadmin;


-- Completed on 2025-08-31 04:08:19

--
-- PostgreSQL database dump complete
--

--
-- Database "tpgr63eventosuytest" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.10 (Debian 16.10-1.pgdg13+1)
-- Dumped by pg_dump version 17.5

-- Started on 2025-08-31 04:08:20

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3481 (class 1262 OID 24576)
-- Name: tpgr63eventosuytest; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tpgr63eventosuytest WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE tpgr63eventosuytest OWNER TO postgres;

\connect tpgr63eventosuytest

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 24732)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 27697)
-- Name: categoria; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.categoria (
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    nombre character varying(120) NOT NULL
);


ALTER TABLE public.categoria OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 217 (class 1259 OID 27704)
-- Name: edicion; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.edicion (
    fechaalta date NOT NULL,
    fechafin date NOT NULL,
    fechainicio date NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    evento_id bigint NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    sigla character varying(20) NOT NULL,
    ciudad character varying(120) NOT NULL,
    nombre character varying(120) NOT NULL,
    pais character varying(120) NOT NULL
);


ALTER TABLE public.edicion OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 218 (class 1259 OID 27711)
-- Name: evento; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.evento (
    fecha_alta date NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    sigla character varying(20) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(300)
);


ALTER TABLE public.evento OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 219 (class 1259 OID 27718)
-- Name: evento_categoria; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.evento_categoria (
    categoria_id bigint NOT NULL,
    evento_id bigint NOT NULL
);


ALTER TABLE public.evento_categoria OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 215 (class 1259 OID 27696)
-- Name: global_seq; Type: SEQUENCE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE SEQUENCE public.global_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.global_seq OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 220 (class 1259 OID 27723)
-- Name: registro; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.registro (
    costo real NOT NULL,
    asistente_id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    edicion_id bigint NOT NULL,
    fecha timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    tipo_registro_id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL
);


ALTER TABLE public.registro OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 221 (class 1259 OID 27730)
-- Name: tipo_registro; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.tipo_registro (
    costo real NOT NULL,
    cupo integer NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    edicion_id bigint NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion character varying(300) NOT NULL
);


ALTER TABLE public.tipo_registro OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 222 (class 1259 OID 27737)
-- Name: usuario; Type: TABLE; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE TABLE public.usuario (
    created_at timestamp(6) without time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    version bigint NOT NULL,
    tipo character varying(31) NOT NULL,
    nickname character varying(40) NOT NULL,
    apellido character varying(120) NOT NULL,
    nombre character varying(120) NOT NULL,
    correo character varying(180) NOT NULL
);


ALTER TABLE public.usuario OWNER TO tpgr63eventosuyadmin;

--
-- TOC entry 3469 (class 0 OID 27697)
-- Dependencies: 216
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.categoria (created_at, id, updated_at, version, nombre) FROM stdin;
2025-08-31 03:38:07.709465	1	2025-08-31 03:38:07.709465	0	CategoriaTest1
2025-08-31 03:38:07.752855	2	2025-08-31 03:38:07.752855	0	CategoriaTest2
\.


--
-- TOC entry 3470 (class 0 OID 27704)
-- Dependencies: 217
-- Data for Name: edicion; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.edicion (fechaalta, fechafin, fechainicio, created_at, evento_id, id, updated_at, version, sigla, ciudad, nombre, pais) FROM stdin;
\.


--
-- TOC entry 3471 (class 0 OID 27711)
-- Dependencies: 218
-- Data for Name: evento; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.evento (fecha_alta, created_at, id, updated_at, version, sigla, nombre, descripcion) FROM stdin;
1969-12-31	2025-08-31 03:38:07.805307	3	2025-08-31 03:38:07.805307	0	SIGLA1	EventoTest1	Lallalal
\.


--
-- TOC entry 3472 (class 0 OID 27718)
-- Dependencies: 219
-- Data for Name: evento_categoria; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.evento_categoria (categoria_id, evento_id) FROM stdin;
1	3
2	3
\.


--
-- TOC entry 3473 (class 0 OID 27723)
-- Dependencies: 220
-- Data for Name: registro; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.registro (costo, asistente_id, created_at, edicion_id, fecha, id, tipo_registro_id, updated_at, version) FROM stdin;
\.


--
-- TOC entry 3474 (class 0 OID 27730)
-- Dependencies: 221
-- Data for Name: tipo_registro; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.tipo_registro (costo, cupo, created_at, edicion_id, id, updated_at, version, nombre, descripcion) FROM stdin;
\.


--
-- TOC entry 3475 (class 0 OID 27737)
-- Dependencies: 222
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: tpgr63eventosuyadmin
--

COPY public.usuario (created_at, id, updated_at, version, tipo, nickname, apellido, nombre, correo) FROM stdin;
\.


--
-- TOC entry 3485 (class 0 OID 0)
-- Dependencies: 215
-- Name: global_seq; Type: SEQUENCE SET; Schema: public; Owner: tpgr63eventosuyadmin
--

SELECT pg_catalog.setval('public.global_seq', 3, true);


--
-- TOC entry 3292 (class 2606 OID 27701)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 3296 (class 2606 OID 27708)
-- Name: edicion edicion_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT edicion_pkey PRIMARY KEY (id);


--
-- TOC entry 3304 (class 2606 OID 27722)
-- Name: evento_categoria evento_categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT evento_categoria_pkey PRIMARY KEY (categoria_id, evento_id);


--
-- TOC entry 3300 (class 2606 OID 27715)
-- Name: evento evento_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- TOC entry 3306 (class 2606 OID 27727)
-- Name: registro registro_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT registro_pkey PRIMARY KEY (id);


--
-- TOC entry 3310 (class 2606 OID 27734)
-- Name: tipo_registro tipo_registro_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT tipo_registro_pkey PRIMARY KEY (id);


--
-- TOC entry 3294 (class 2606 OID 27703)
-- Name: categoria uk_categoria_nombre; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT uk_categoria_nombre UNIQUE (nombre);


--
-- TOC entry 3298 (class 2606 OID 27710)
-- Name: edicion uk_edicion_nombre_evento; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT uk_edicion_nombre_evento UNIQUE (nombre, evento_id);


--
-- TOC entry 3302 (class 2606 OID 27717)
-- Name: evento uk_evento_nombre; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT uk_evento_nombre UNIQUE (nombre);


--
-- TOC entry 3308 (class 2606 OID 27729)
-- Name: registro uk_registro_asistente_edicion; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT uk_registro_asistente_edicion UNIQUE (asistente_id, edicion_id);


--
-- TOC entry 3312 (class 2606 OID 27736)
-- Name: tipo_registro uk_tiporeg_nombre_edicion; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT uk_tiporeg_nombre_edicion UNIQUE (nombre, edicion_id);


--
-- TOC entry 3315 (class 2606 OID 27745)
-- Name: usuario usuario_nickname_key; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_nickname_key UNIQUE (nickname);


--
-- TOC entry 3317 (class 2606 OID 27743)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3313 (class 1259 OID 27746)
-- Name: ix_usuario_nickname; Type: INDEX; Schema: public; Owner: tpgr63eventosuyadmin
--

CREATE INDEX ix_usuario_nickname ON public.usuario USING btree (nickname);


--
-- TOC entry 3318 (class 2606 OID 27747)
-- Name: edicion fk_edicion_evento; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.edicion
    ADD CONSTRAINT fk_edicion_evento FOREIGN KEY (evento_id) REFERENCES public.evento(id);


--
-- TOC entry 3319 (class 2606 OID 27752)
-- Name: evento_categoria fk_ev_cat_categoria; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT fk_ev_cat_categoria FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 3320 (class 2606 OID 27757)
-- Name: evento_categoria fk_ev_cat_evento; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.evento_categoria
    ADD CONSTRAINT fk_ev_cat_evento FOREIGN KEY (evento_id) REFERENCES public.evento(id);


--
-- TOC entry 3321 (class 2606 OID 27762)
-- Name: registro fk_reg_asistente; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_asistente FOREIGN KEY (asistente_id) REFERENCES public.usuario(id);


--
-- TOC entry 3322 (class 2606 OID 27767)
-- Name: registro fk_reg_edicion; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_edicion FOREIGN KEY (edicion_id) REFERENCES public.edicion(id);


--
-- TOC entry 3323 (class 2606 OID 27772)
-- Name: registro fk_reg_tiporeg; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.registro
    ADD CONSTRAINT fk_reg_tiporeg FOREIGN KEY (tipo_registro_id) REFERENCES public.tipo_registro(id);


--
-- TOC entry 3324 (class 2606 OID 27777)
-- Name: tipo_registro fk_tiporeg_edicion; Type: FK CONSTRAINT; Schema: public; Owner: tpgr63eventosuyadmin
--

ALTER TABLE ONLY public.tipo_registro
    ADD CONSTRAINT fk_tiporeg_edicion FOREIGN KEY (edicion_id) REFERENCES public.edicion(id);


--
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 3481
-- Name: DATABASE tpgr63eventosuytest; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE tpgr63eventosuytest TO tpgr63eventosuyadmin;


--
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO tpgr63eventosuyadmin;


-- Completed on 2025-08-31 04:08:20

--
-- PostgreSQL database dump complete
--

-- Completed on 2025-08-31 04:08:20

--
-- PostgreSQL database cluster dump complete
--

