package it.smc.corso.lifecycle.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import it.smc.corso.lifecycle.constants.LifecyclePortletKeys;

/**
 * @author amusarra
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.show-portlet-access-denied=true",
		"javax.portlet.display-name=lifecycle-portlet Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LifecyclePortletKeys.Lifecycle,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-public-render-parameter=tag;http://my.prp.tld/ns"
	},
	service = Portlet.class
)
public class LifecyclePortlet extends MVCPortlet {
	@Override
	public void init() throws PortletException {
		// TODO Auto-generated method stub
		super.init();
		
		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo init()");
		}
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		super.render(renderRequest, renderResponse);

		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo render()");
		}

	}
	
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub

		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo processAction()");
			_log.info(ParamUtil.getString(actionRequest, "name"));
		}
		
		super.processAction(actionRequest, actionResponse);
		
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		super.serveResource(resourceRequest, resourceResponse);

		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo serveResource()");
		}
		
		switch (resourceRequest.getResourceID()) {
			case "updateMessageResource":
				if(_log.isInfoEnabled()) {
					_log.info("Sono dentro " + resourceRequest.getResourceID());
				}
				
				JSONObject jsonResponse = JSONFactoryUtil.createJSONObject();
				jsonResponse.put("id", PortalUUIDUtil.getPortalUUID());
				jsonResponse.put("name", "Antonio Musarra");
				
				writeJSON(resourceRequest, resourceResponse, jsonResponse);
				
				break;
	
			default:
				break;
		}

	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();

		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo destroy()");
		}

	}

	public void updateMessage(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub

		if (_log.isInfoEnabled()) {
			_log.info("Sono il metodo updateMessage()");
			_log.info(ParamUtil.getString(actionRequest, "name"));
		}
		
	}

	private static Log _log = 
			LogFactoryUtil.getLog(LifecyclePortlet.class);
}