package the.postbooking.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.BookingApi;

/**
 * Created by Emanuele Tattolo on 23/08/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class BookingController implements BookingApi {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

}