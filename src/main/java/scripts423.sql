select student.name as studentName, student.age as age,
       faculty.name as faculty, faculty.color as color
from student inner join faculty on student.faculty_id = faculty.id

select student.id as id, student.name as studentName, student.age as age
from student inner join public.avatar a on student.id = a.student_id