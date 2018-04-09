/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.web.portlet.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import it.smc.lrtraining.manage.tasks.constants.ManageTasksPortletKeys;
import it.smc.lrtraining.manage.tasks.constants.ManageTasksWebKeys;
import it.smc.lrtraining.manage.tasks.model.Task;
import it.smc.lrtraining.manage.tasks.service.TaskService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author SMC Treviso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ManageTasksPortletKeys.MANAGE_TASKS,
		"mvc.command.name=/manage-tasks/task/edit"
	},
	service = MVCRenderCommand.class
)
public class EditTaskRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		Task task = null;

		long taskId = ParamUtil.getLong(renderRequest, "taskId");

		if (taskId > 0) {
			try {
				task = _taskService.getTask(taskId);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		renderRequest.setAttribute(ManageTasksWebKeys.TASK, task);

		return "/edit_task.jsp";
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditTaskRenderCommand.class);

	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	private TaskService _taskService;

}