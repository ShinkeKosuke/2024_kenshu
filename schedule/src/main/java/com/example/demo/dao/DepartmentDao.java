package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Repository
public class DepartmentDao implements BaseDao<Department> {
	@Autowired
	DepartmentRepository repository;

	@Override
	public List<Department> findAll() {
		return repository.findAll();
	}

	@Override
	public Department findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Department department) {
		this.repository.save(department);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Department department = this.findById(id);
			this.repository.deleteById(department.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
}
