/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.service.ServiceContext;

import it.smc.lrtraining.manage.tasks.constants.ActionKeys;
import it.smc.lrtraining.manage.tasks.model.Task;
import it.smc.lrtraining.manage.tasks.service.base.TaskServiceBaseImpl;
import it.smc.lrtraining.manage.tasks.service.permission.TaskPermissionChecker;
import it.smc.lrtraining.manage.tasks.service.permission.TaskResourcePermissionChecker;

import java.util.List;

/**
 * @author Michele Perissinotto
 */
@ProviderType
public class TaskServiceImpl extends TaskServiceBaseImpl {

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	public Task addTask(
			String title, String description, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear, long taskUserId,
			boolean completed, ServiceContext serviceContext)
		throws PortalException {

		TaskResourcePermissionChecker.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_TASK);

		return taskLocalService.addTask(
			getUserId(), title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, taskUserId, completed,
			serviceContext);
	}

	public Task deleteTask(long taskId) throws PortalException {
		TaskPermissionChecker.check(
			getPermissionChecker(), taskId, ActionKeys.DELETE);

		return taskLocalService.deleteTask(taskId);
	}

	public Task getTask(long taskId) throws PortalException {
		TaskPermissionChecker.check(
			getPermissionChecker(), taskId, ActionKeys.VIEW);

		return taskLocalService.getTask(taskId);
	}

	public List<Task> getTasks(
		long companyId, long groupId, int start, int end) {

		return taskPersistence.filterFindByC_G(companyId, groupId, start, end);
	}

	public List<Task> getTasks(
		long companyId, long groupId, int status, int start, int end) {

		return taskPersistence.filterFindByC_G_S(
			companyId, groupId, status, start, end);
	}

	public int getTasksCount(long companyId, long groupId) {
		return taskPersistence.filterCountByC_G(companyId, groupId);
	}

	public int getTasksCount(long companyId, long groupId, int status) {
		return taskPersistence.filterCountByC_G_S(companyId, groupId, status);
	}

	public Task updateTask(
			long taskId, String title, String description,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, long taskUserId, boolean completed,
			ServiceContext serviceContext)
		throws PortalException {

		TaskPermissionChecker.check(
			getPermissionChecker(), taskId, ActionKeys.UPDATE);

		return taskLocalService.updateTask(
			getUserId(), taskId, title, description, expirationDateMonth,
			expirationDateDay, expirationDateYear, taskUserId, completed,
			serviceContext);
	}

}