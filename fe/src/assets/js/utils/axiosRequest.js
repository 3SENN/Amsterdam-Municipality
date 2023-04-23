import axios from "axios";

export class AxiosRequest{

    ERR_NETWORK = "ERR_NETWORK"
    statusCodes = require("./httpStatusCodes")

    async doRequest(url, method, data = {}, params = {}) {
        let response;
        try {
            response = await axios({
                method: method,
                url: url,
                // data is key for the body
                data: data,
                params: params,
                headers: {
                    'Content-Type': 'application/json',
                }
            })

            if (response.status === this.statusCodes.HTTP_OK_CODE) {
                return {data: response.data, statusCode: this.statusCodes.HTTP_OK_CODE}
            }
        } catch (error) {
            console.error(error)
            console.error("Error occurred while doing the request")
            if (error.code === this.ERR_NETWORK) throw {
                message: error.message, statusCode: this.statusCodes.SERVICE_UNAVAILABLE
            }
            throw  {message: error.response.data}
        }


    }

}