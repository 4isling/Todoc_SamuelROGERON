package com.example.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.example.todoc.database.dao.ProjectDao;
import com.example.todoc.model.Project;

public class ProjectDataRepository {
    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

    public LiveData<Project> getProject(long project_id){
        return this.projectDao.getProject(project_id);
    }
}
