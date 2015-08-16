In each module the team leader puts the daily tasks.
	
	TeamLeader leader = new TeamLeader();
	leader.getMembersList();
	foreach (GroupMember member : membersList){
		member.setTask();
		member.setPeriodToCompleteTask();
	}
	leader.checkMemberStatus();
	leader.commitResult();

Repeat 'till Wednesday :)
