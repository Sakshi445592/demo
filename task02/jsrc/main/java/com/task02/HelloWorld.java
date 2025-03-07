package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "${lambdas_alias_name}",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
		String path = (String) input.get("path"); // Extract the path from the request
		String method = (String) input.get("httpMethod"); // Extract the HTTP method (GET, POST, etc.)
		Map<String, Object> resultMap = new HashMap<>();

		// Handle /hello endpoint with GET method
		if ("/hello".equals(path) && "GET".equalsIgnoreCase(method)) {
			resultMap.put("statusCode", 200);
			resultMap.put("message", "Hello from Lambda");
		} else {
			// Handle all other endpoints with a 400 Bad Request error
			resultMap.put("statusCode", 400);
			resultMap.put("message", String.format("Bad Request: {path=%s, method=%s}", path, method));
		}

		return resultMap;
	}
}
