create index IX_95DEB9AB on MT_Task (companyId, groupId, status);
create index IX_290B76E4 on MT_Task (companyId, taskUserId);
create index IX_8E0F6DBF on MT_Task (companyId, userId);
create index IX_7754A8F7 on MT_Task (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B1110139 on MT_Task (uuid_[$COLUMN_LENGTH:75$], groupId);