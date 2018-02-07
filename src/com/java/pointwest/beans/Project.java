package com.java.pointwest.beans;

import java.util.List;

public class Project {
	private String projectAlias;
	private String projectName;
	private List<Integer> teamMembersID;
	
	// constructors for one time setters only
	public Project (String projName, String projAlias) {
		projectName = projName;
	}
	
	public String getProjectAlias() {
		return projectAlias;
	}

	public String getProjectName() {
		return projectName;
	}

	public List<Integer> getTeamMembersID() {
		return teamMembersID;
	}

	public void addTeamMember(int empID) {
		teamMembersID.add(empID);
	}
}
