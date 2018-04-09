/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */

package it.smc.lrtraining.manage.tasks.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import it.smc.lrtraining.manage.tasks.service.TaskService;

import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michele Perissinotto
 */
@Component(immediate = true)
public class ManageTasksSAPEntryActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("activate()");
		}

		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new ManageTasksPortalInstanceLifecycleListener(), null);
	}

	protected void addSAPEntry(long companyId) throws PortalException {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, _SAP_ENTRY_NAME);

		if (sapEntry != null) {
			return;
		}

		StringBundler sb = new StringBundler(2);

		sb.append(TaskService.class.getName());
		sb.append("#get*");

		String allowedServiceSignatures = sb.toString();

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				ResourceBundleUtil.getResourceBundleLoader(
					"content.Language",
					ManageTasksSAPEntryActivator.class.getClassLoader()),
				LanguageResources.RESOURCE_BUNDLE_LOADER);

		Map<Locale, String> titleMap = ResourceBundleUtil.getLocalizationMap(
			resourceBundleLoader,
			"service-access-policy-entry-default-manage-tasks-title");

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId),
			allowedServiceSignatures, true, true, _SAP_ENTRY_NAME, titleMap,
			new ServiceContext());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final String _SAP_ENTRY_NAME = "MANAGE_TASKS_DEFAULT";

	private static Log _log = LogFactoryUtil.getLog(
		ManageTasksSAPEntryActivator.class);

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private SAPEntryLocalService _sapEntryLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_serviceRegistration;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private UserLocalService _userLocalService;

	private class ManageTasksPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		public void portalInstanceRegistered(Company company) throws Exception {
			try {
				addSAPEntry(company.getCompanyId());
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add service access policy entry for company " +
						company.getCompanyId(),
					pe);
			}
		}

	}

}