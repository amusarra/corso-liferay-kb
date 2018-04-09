<%@ include file="init.jsp" %>

<p>
	<b><liferay-ui:message key="lifecycle-portlet.caption"/></b>
	
	<liferay-portlet:actionURL name="updateMessage" var="updateMessageURL"/>
	<liferay-portlet:resourceURL id="updateMessageResource" var="updateMessageResourceURL"/>
	
	<aui:form action="<%=updateMessageURL%>" method="post" name="frmHelloWorld">
		<aui:input name="name" type="text"/>
		<aui:button type="submit" name="submit" value="Invia" />
		<aui:button name="callAjax" value="Chiamata Ajax" />
		<aui:a href="<%=updateMessageResourceURL %>">Richiedi Risorsa</aui:a>
	</aui:form>
</p>

<aui:script>
YUI().use(
		  'aui-io-request',
		  function (Y) {
		    Y.io.request(
		      '<%=updateMessageResourceURL%>',
		      {
		        on: {
		          success: function() {
		            var data = this.get('responseData');
		            alert(data);
		          }
		        }
		      }
		    );
		  }
		);
</aui:script>