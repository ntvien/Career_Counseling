package group12.career_counseling.web_service.vertx.rest.exception;

public class ExceptionMessages {

    //User
    public static String FieldNullException = "Field is be null: ";
    public static String UserIdNotFoundException = "UserId is not found";
    public static String PhoneNumberExistsException = "PhoneNumber is exists";
    public static String CreateUserException = "Create User is failure";
    public static String UpdateUserFailureException = "Update User is failure";
    public static String ChangePasswordFailureException = "Change password is failure";
    public static String OldPasswordWrongException = "Old password is wrong";
    public static String RegisterAccountValidationException = "Register account need two field: phoneNumber and password";
    public static String MissUserTypeException = "Miss type_user";

    //authentication
    public static String AuthenticationValidateException = "Authentication need two field: phoneNumber and password";
    public static String PhoneNumberNotFoundException = "PhoneNumber is not found";
    public static String WrongPasswordException = "Wrong password";
    public static String BadToken = "Bad token";

    ///Group
    public static String GroupIdNotFoundException = "UserId is not found";
    public static String NumberMemberInGroupException = "Number of member in group need equal or greater than two";
    public static String GroupInsertException = "Create group false";
    //message
    public static String InsertMessageFalse = "Insert Message is False";

    //University
    public static String UnisersityIdNotFound = "University id not found";

    public static final String USERNAME_EXISTED = "username_existed";
    public static final String REQUIRED_FIELDS_ARE_NULL = "required_fields_are_null";
    public static final String INCORRECT_USERNAME = "incorrect_username";
    public static final String INCORRECT_PASSWORD = "incorrect_password";
    public static final String INCORRECT_PHONE = "incorrect_phone";
    public static final String INVALID_TOKEN = "invalid_token";
    public static final String INVALID_PHONE = "invalid_phone";
    public static final String INVALID_EMAIL = "invalid_email";
    public static final String PHONE_EXISTED = "phone_existed";
}

