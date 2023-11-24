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

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // Add debug trace or any other information you want to include
                String debugMessage = "Internal Server Error. Debug trace: ...";
                return ResponseEntity.status(statusCode).body(debugMessage);
            }
        }

        // Default error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Resource not found.");
    }

    public String getErrorPath() {
        return "/error";
    }
}
