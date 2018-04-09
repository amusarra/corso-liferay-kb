/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PortalUtil;

import it.smc.lrtraining.manage.tasks.constants.ActionKeys;
import it.smc.lrtraining.manage.tasks.constants.ManageTasksPortletKeys;
import it.smc.lrtraining.manage.tasks.model.Task;
import it.smc.lrtraining.manage.tasks.service.TaskLocalService;
import it.smc.lrtraining.manage.tasks.service.TaskService;
import it.smc.lrtraining.manage.tasks.service.permission.TaskPermissionChecker;
import it.smc.lrtraining.manage.tasks.service.permission.TaskResourcePermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michele Perissinotto
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ManageTasksPortletKeys.MANAGE_TASKS},
	service = AssetRendererFactory.class
)
public class TaskAssetRendererFactory extends BaseAssetRendererFactory<Task> {

	public static final String CLASS_NAME = Task.class.getName();

	public static final String TYPE = "task";

	public TaskAssetRendererFactory() {
		setClassName(CLASS_NAME);
		setLinkable(true);
		setPortletId(ManageTasksPortletKeys.MANAGE_TASKS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<Task> getAssetRenderer(long classPK, int type)
		throws PortalException {

		Task task = _taskService.getTask(classPK);

		TaskAssetRenderer taskAssetRenderer = new TaskAssetRenderer(task);

		taskAssetRenderer.setAssetRendererType(type);

		return taskAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classTypeId) {

		try {
			long plid = PortalUtil.getPlidFromPortletId(
				getGroup(liferayPortletRequest).getGroupId(),
				ManageTasksPortletKeys.MANAGE_TASKS);

			PortletURL portletURL = PortletURLFactoryUtil.create(
				liferayPortletRequest, ManageTasksPortletKeys.MANAGE_TASKS,
				plid, PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/manage-tasks/task/edit");

			return portletURL;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return TaskResourcePermissionChecker.contains(
			permissionChecker, groupId, ActionKeys.ADD_TASK);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		Task task = _taskLocalService.getTask(classPK);

		return TaskPermissionChecker.contains(
			permissionChecker, task, actionId);
	}

	private static Log _log = LogFactoryUtil.getLog(
		TaskAssetRendererFactory.class);

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.web.symbolicname=it.smc.lrtraining.manage.tasks.web)"
	)
	private ServletContext _servletContext;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private TaskLocalService _taskLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private TaskService _taskService;

}