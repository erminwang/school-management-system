import React, { Component } from 'react';
import { signup, checkUsernameAvailability, checkEmailAvailability } from '../../util/APIUtils';
import './Signup.css';
import { Link } from 'react-router-dom';
import { 
    NAME_MIN_LENGTH, NAME_MAX_LENGTH, 
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH,
    GENDER_TYPES,
    USER_TYPES
} from '../../constants';

import { Form, Input, Radio, Button, notification } from 'antd';
const FormItem = Form.Item;
const RadioGroup = Radio.Group;

let initialState = {
    signupCompleted: false,
    firstName: {
        value: ''
    },
    lastName: {
        value: ''
    },
    username: {
        value: ''
    },
    email: {
        value: ''
    },
    password: {
        value: ''
    },
    gender: {
        value: ''
    },
    userType: {
        value: ''
    }
};

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = initialState;
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateUsernameAvailability = this.validateUsernameAvailability.bind(this);
        this.validateEmailAvailability = this.validateEmailAvailability.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

        this.setState({
            [inputName] : {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();
    
        const signupRequest = {
            firstName: this.state.firstName.value,
            lastName: this.state.lastName.value,
            email: this.state.email.value,
            username: this.state.username.value,
            password: this.state.password.value,
            gender: this.state.gender.value,
            userType: this.state.userType.value
        };
        signup(signupRequest)
        .then(response => {
            if(response.success) {
                notification.success({
                    message: 'College Board',
                    description: "Success! A new user has been successfully registered.",
                });  
                this.setState({
                    signupCompleted: true
                });      
                // this.props.history.push("/login");
            } else {
                var error = new Error('Unexpected Error: User not created');
                error.response = response;
                throw error;
            }
        }).catch(error => {
            console.log(error);
            notification.error({
                message: 'College Board',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
        });
    }

    isFormInvalid() {
        return !(this.state.firstName.validateStatus === 'success' &&
            this.state.lastName.validateStatus === 'success' &&
            this.state.username.validateStatus === 'success' &&
            this.state.email.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success' &&
            this.state.gender.validateStatus === 'success' &&
            this.state.userType.validateStatus === 'success'
        );
    }

    render() {
        if(this.state.signupCompleted) {
            return (
                <div>
                    <h1>User Created</h1>
                    <h3>First Name: {this.state.firstName.value}</h3>
                    <h3>Last Name: {this.state.lastName.value}</h3>
                    <h3>Username: {this.state.username.value}</h3>
                    <h3>Email: {this.state.email.value}</h3>
                    <h3>Gender: {this.state.gender.value}</h3>
                    <h3>User Type: {this.state.userType.value}</h3>
                    <Button type="primary" 
                            htmlType="submit" 
                            size="large" 
                            className="signup-form-button"
                            onClick={() => this.setState(initialState)}
                    >Back</Button>
                </div>
            )
        }
        return (
            <div className="signup-container">
                <h1 className="page-title">Register User</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem 
                            label="First Name"
                            validateStatus={this.state.firstName.validateStatus}
                            help={this.state.firstName.errorMsg}>
                            <Input 
                                size="large"
                                name="firstName"
                                autoComplete="off"
                                placeholder="User's first name (2 ~ 40 characters)"
                                value={this.state.firstName.value} 
                                onChange={(event) => this.handleInputChange(event, this.validateFirstName)} />    
                        </FormItem>
                        <FormItem 
                            label="Last Name"
                            validateStatus={this.state.lastName.validateStatus}
                            help={this.state.lastName.errorMsg}>
                            <Input 
                                size="large"
                                name="lastName"
                                autoComplete="off"
                                placeholder="User's last name (2 ~ 40 characters)"
                                value={this.state.lastName.value} 
                                onChange={(event) => this.handleInputChange(event, this.validateLastName)} />    
                        </FormItem>
                        <FormItem label="Username"
                            hasFeedback
                            validateStatus={this.state.username.validateStatus}
                            help={this.state.username.errorMsg}>
                            <Input 
                                size="large"
                                name="username" 
                                autoComplete="off"
                                placeholder="A unique username"
                                value={this.state.username.value} 
                                onBlur={this.validateUsernameAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateUsername)} />    
                        </FormItem>
                        <FormItem 
                            label="Email"
                            hasFeedback
                            validateStatus={this.state.email.validateStatus}
                            help={this.state.email.errorMsg}>
                            <Input 
                                size="large"
                                name="email" 
                                type="email" 
                                autoComplete="off"
                                placeholder="User's email"
                                value={this.state.email.value} 
                                onBlur={this.validateEmailAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateEmail)} />    
                        </FormItem>
                        <FormItem 
                            label="Password"
                            validateStatus={this.state.password.validateStatus}
                            help={this.state.password.errorMsg}>
                            <Input 
                                size="large"
                                name="password" 
                                type="password"
                                autoComplete="off"
                                placeholder="A password between 6 to 20 characters" 
                                value={this.state.password.value} 
                                onChange={(event) => this.handleInputChange(event, this.validatePassword)} />    
                        </FormItem>
                        <FormItem 
                            label="Gender"
                            validateStatus={this.state.gender.validateStatus}
                            help={this.state.gender.errorMsg}>
                            <RadioGroup
                                buttonStyle="solid"
                                name="gender"
                                onChange = {(event) => this.handleInputChange(event, this.validateGender)}
                                >
                                <Radio.Button value="MALE">Male</Radio.Button>
                                <Radio.Button value="FEMALE">Female</Radio.Button>
                                <Radio.Button value="SECRET">Secret</Radio.Button>
                                <Radio.Button value="WITHOUT">None</Radio.Button>
                            </RadioGroup>
                        </FormItem>
                        <FormItem 
                            label="User Type"
                            validateStatus={this.state.userType.validateStatus}
                            help={this.state.userType.errorMsg}>
                            <RadioGroup
                                buttonStyle="solid"
                                name="userType"
                                onChange = {(event) => this.handleInputChange(event, this.validateUserType)}
                                >
                                <Radio.Button value="STUDENT">Student</Radio.Button>
                                <Radio.Button value="INSTRUCTOR">Instructor</Radio.Button>
                                <Radio.Button value="ADMIN">Administrator</Radio.Button>
                            </RadioGroup>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                className="signup-form-button"
                                disabled={this.isFormInvalid()}>Register</Button>
                            Go to Login Page <Link to="/login">Login</Link>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }

    // Validation Functions

    validateFirstName = (name) => {
        if(name.length < 0) {
            return {
                validateStatus: 'error',
                errorMsg: `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`
            }
        } else if (name.length > NAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
              };            
        }
    }

    validateLastName = (name) => {
        if(name.length < NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`
            }
        } else if (name.length > NAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
              };            
        }
    }

    validateEmail = (email) => {
        if(!email) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email may not be empty'                
            }
        }

        const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
        if(!EMAIL_REGEX.test(email)) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email not valid'
            }
        }

        if(email.length > EMAIL_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Email is too long (Maximum ${EMAIL_MAX_LENGTH} characters allowed)`
            }
        }

        return {
            validateStatus: null,
            errorMsg: null
        }
    }

    validateUsername = (username) => {
        if(username.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`
            }
        } else if (username.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    }

    validateUsernameAvailability() {
        // First check for client side errors in username
        const usernameValue = this.state.username.value;
        const usernameValidation = this.validateUsername(usernameValue);

        if(usernameValidation.validateStatus === 'error') {
            this.setState({
                username: {
                    value: usernameValue,
                    ...usernameValidation
                }
            });
            return;
        }

        this.setState({
            username: {
                value: usernameValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkUsernameAvailability(usernameValue)
        .then(response => {
            if(response.available) {
                this.setState({
                    username: {
                        value: usernameValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            } else {
                this.setState({
                    username: {
                        value: usernameValue,
                        validateStatus: 'error',
                        errorMsg: 'This username is already taken'
                    }
                });
            }
        }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                username: {
                    value: usernameValue,
                    validateStatus: 'success',
                    errorMsg: null
                }
            });
        });
    }

    validateEmailAvailability() {
        // First check for client side errors in email
        const emailValue = this.state.email.value;
        const emailValidation = this.validateEmail(emailValue);

        if(emailValidation.validateStatus === 'error') {
            this.setState({
                email: {
                    value: emailValue,
                    ...emailValidation
                }
            });    
            return;
        }

        this.setState({
            email: {
                value: emailValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkEmailAvailability(emailValue)
        .then(response => {
            if(response.available) {
                this.setState({
                    email: {
                        value: emailValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            } else {
                this.setState({
                    email: {
                        value: emailValue,
                        validateStatus: 'error',
                        errorMsg: 'This Email is already registered'
                    }
                });
            }
        }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                email: {
                    value: emailValue,
                    validateStatus: 'success',
                    errorMsg: null
                }
            });
        });
    }

    validatePassword = (password) => {
        if(password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };            
        }
    }

    validateGender = (gender) => {
        if(!GENDER_TYPES.includes(gender)) {
            return {
                validateStatus: 'error',
                errorMsg: `User Type is not valid (One of MALE, FEMALE, SECRET, WITHOUT)`
            };
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            }; 
        }
    }

    validateUserType = (userType) => {
        if(!USER_TYPES.includes(userType)) {
            return {
                validateStatus: 'error',
                errorMsg: `User Type is not valid (One of student, instructor, admin)`
            };
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            }; 
        }
    }

}

export default Signup;