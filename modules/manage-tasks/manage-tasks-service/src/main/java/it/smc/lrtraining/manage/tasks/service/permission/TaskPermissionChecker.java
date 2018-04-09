/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import it.smc.lrtraining.manage.tasks.model.Task;
import it.smc.lrtraining.manage.tasks.service.TaskLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michele Perissinotto
 */
@Component(
	immediate = true,
	property = {"model.class.name=it.smc.lrtraining.manage.tasks.model.Task"}
)
public class TaskPermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long taskId, String actionId)
		throws PortalException {

		Task task = _taskLocalService.getTask(taskId);

		check(permissionChecker, task, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Task task, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, task, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Task.class.getName(), task.getTaskId(),
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long taskId, String actionId)
		throws PortalException {

		Task task = _taskLocalService.getTask(taskId);

		return contains(permissionChecker, task, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, Task task, String actionId) {

		/*if (permissionChecker.hasOwnerPermission(
				task.getCompanyId(), Task.class.getName(), task.getTaskId(),
				task.getUserId(), actionId)) {

			return true;
		}*/

		return permissionChecker.hasPermission(
			task.getGroupId(), Task.class.getName(), task.getTaskId(),
			actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(policyOption=ReferencePolicyOption.GREEDY, unbind = "-")
	protected void setTaskLocalService(TaskLocalService taskLocalService) {
		_taskLocalService = taskLocalService;
	}

	private static TaskLocalService _taskLocalService;

}