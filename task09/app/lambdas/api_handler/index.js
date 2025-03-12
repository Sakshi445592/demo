const axios = require("axios");

exports.handler = async (event) => {
    const { rawPath, requestContext } = event;

    if (rawPath !== "/weather" || requestContext.http.method !== "GET") {
        return {
            statusCode: 400,
            body: JSON.stringify({
                statusCode: 400,
                message: `Bad request syntax or unsupported method. Request path: ${rawPath}. HTTP method: ${requestContext.http.method}`
            }),
            headers: { "content-type": "application/json" },
            isBase64Encoded: false
        };
    }

    try {
        const response = await axios.get("https://api.open-meteo.com/v1/forecast?latitude=50.4375&longitude=30.5&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m&current=temperature_2m,wind_speed_10m");
        return {
            statusCode: 200,
            body: JSON.stringify(response.data),
            headers: { "content-type": "application/json" },
            isBase64Encoded: false
        };
    } catch (error) {
        return {
            statusCode: 500,
            body: JSON.stringify({ error: "Failed to fetch weather data" }),
            headers: { "content-type": "application/json" },
            isBase64Encoded: false
        };
    }
};
