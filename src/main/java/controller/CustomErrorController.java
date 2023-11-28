package controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() && exception != null) {
                // If it's an internal server error and an exception is present, include the error message
                Throwable throwable = (Throwable) exception;
                String errorMessage = throwable.getMessage();

                // Include the error message in the response
                String responseMessage = "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; align-items: center; justify-content: center; height: 100vh; }" +
                        ".error-container { text-align: center; padding: 20px; background-color: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                        "h1 { color: #d9534f; } p { color: #333; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"error-container\">" +
                        "<h1>Erreur</h1>" +
                        "<p>Oups petite erreur... : " + errorMessage + "</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

                return ResponseEntity.status(statusCode).body(responseMessage);
            }
        }

        // Default error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Resource not found.");
    }

    public String getErrorPath() {
        return "/error";
    }
}
