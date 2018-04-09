/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import it.smc.lrtraining.manage.tasks.constants.ManageTasksPortletKeys;
import it.smc.lrtraining.manage.tasks.exception.ManageTasksException;
import it.smc.lrtraining.manage.tasks.exception.TaskTitleException;
import it.smc.lrtraining.manage.tasks.model.Task;
import it.smc.lrtraining.manage.tasks.service.TaskService;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michele Perissinotto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=manage-tasks-portlet",
		"com.liferay.portlet.display-category=category.training",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Manage Tasks",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ManageTasksPortletKeys.MANAGE_TASKS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class ManageTasksPortlet extends MVCPortlet {

	public void deleteTask(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long taskId = ParamUtil.getLong(actionRequest, "taskId");

		_taskService.deleteTask(taskId);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		if (copyRequestParameters || !SessionErrors.isEmpty(actionRequest)) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	public void updateTask(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Task.class.getName(), actionRequest);

		long taskId = ParamUtil.getLong(actionRequest, "taskId");

		String title = ParamUtil.getString(actionRequest, "title");
		String description = ParamUtil.getString(actionRequest, "description");
		long taskUserId = ParamUtil.getLong(actionRequest, "taskUserId");
		boolean completed = ParamUtil.getBoolean(actionRequest, "completed");

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");

		if (taskId > 0) {
			_taskService.updateTask(
				taskId, title, description, expirationDateMonth,
				expirationDateDay, expirationDateYear, taskUserId, completed,
				serviceContext);

			SessionMessages.add(actionRequest, "taskUpdated");
		}
		else {
			_taskService.addTask(
				title, description, expirationDateMonth, expirationDateDay,
				expirationDateYear, taskUserId, completed, serviceContext);

			SessionMessages.add(actionRequest, "taskAdded");
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	@Override
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof ManageTasksException ||
			cause instanceof TaskTitleException) {

			return true;
		}

		return false;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private TaskService _taskService;

}