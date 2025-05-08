-- liquibase formatted sql

-- changeset kadyrovas:1
CREATE INDEX student_name_index ON STUDENT(name);
CREATE INDEX faculty_name_and_color_index on FACULTY(name, color);