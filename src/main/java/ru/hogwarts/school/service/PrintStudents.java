package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Arrays;
import java.util.List;

class PrintStudents extends Thread{
    private List<Student> studentList;
    private int[] indexes;
    private boolean synchron;
    public PrintStudents(List<Student> studentList, int[] indexes, boolean synchron){
        this.studentList = studentList;
        this.indexes = indexes;
        this.synchron = synchron;
    }

    @Override
    public void run(){
        if (synchron) {
            Arrays.stream(indexes).forEach(i -> printStudentSynchronized(studentList.get(i)));
        }else{
            Arrays.stream(indexes).forEach(i -> printStudentParallel(studentList.get(i)));
        }
    }

    public static void printStudentParallel(Student student){
        System.out.println(student + " from thread " + Thread.currentThread().getName());
    }
    public static synchronized void printStudentSynchronized(Student student){
        System.out.println(student + " from thread " + Thread.currentThread().getName());
    }

}
