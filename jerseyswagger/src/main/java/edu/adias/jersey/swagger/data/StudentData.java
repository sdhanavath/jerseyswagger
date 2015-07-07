/**
 *  Copyright 2015 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package edu.adias.jersey.swagger.data;

import java.util.ArrayList;
import java.util.List;

import edu.adias.jersey.swagger.model.Student;

public class StudentData {
  static List<Student> students = new ArrayList<Student>();

  static {
	  students.add(createStudent(100, "first name 1", "last name 1","123-456-7890", 1));
	  students.add(createStudent(101, "first name 1", "last name 1","123-456-7891", 1));
	  students.add(createStudent(102, "first name 1", "last name 1","123-456-7892", 1));
	  students.add(createStudent(103, "first name 1", "last name 1","123-456-7893", 1));
	  students.add(createStudent(104, "first name 1", "last name 1","123-456-7894", 1));
	  students.add(createStudent(105, "first name 1", "last name 1","123-456-7895", 1));
	  students.add(createStudent(106, "first name 1", "last name 1","123-456-7896", 1));
	  students.add(createStudent(107, "first name 1", "last name 1","123-456-7897", 1));
   
  }

  public Student findStudentById(long id) {
    for (Student student : students) {
      if (student.getId()==id) {
        return student;
      }
    }
    return null;
  }

  public void addStudent(Student student) {
    if (students.size() > 0) {
      for (int i = students.size() - 1; i >= 0; i--) {
        if (students.get(i).getId()==student.getId()) {
          students.remove(i);
        }
      }
    }
    students.add(student);
  }

  public void removeStudent(long id) {
    if (students.size() > 0) {
      for (int i = students.size() - 1; i >= 0; i--) {
        if (students.get(i).getId()==id) {
          students.remove(i);
        }
      }
    }
  }

  private static Student createStudent(long id, String firstName,
      String lastName, String phone, int status) {
    Student student = new Student();
    student.setId(id);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setPhone(phone);
    student.setStatus(status);
    return student;
  }
}