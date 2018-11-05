import React from 'react';
import {
    Route,
    Redirect
  } from "react-router-dom";
import { STUDENT, INSTRUCTOR, ADMIN } from "../constants/index";
import Login from '../user/login/Login';
  
  
const PrivateRoute = ({ component: Component, isAuthenticated, ...rest }) => (
    <Route
      {...rest}
      render={props =>
        isAuthenticated ? (
          <Component {...rest} {...props} />
        ) : (
          <Redirect
            to={{
              pathname: '/',
              state: { from: props.location }
            }}
          />
        )
      }
    />
);

const UnauthenticatedRoute = ({ isAuthenticated, userType, ...rest }) => {
  if(isAuthenticated) {
    switch(userType) {
      case STUDENT:
        return (
          <Route 
            {...rest}
            render={props => (<Redirect 
                                to={{
                                  pathname: '/student/me'
                                }}
                                from="/"
                              />)}>
          </Route>
        );
      case INSTRUCTOR:
        return (
          <Route 
            {...rest}
            render={props => (
                              <Redirect 
                                to={{
                                  pathname: '/instructor/me'
                                }}
                                from="/"
                              />
          )}>
          </Route>
        );
      case ADMIN:
        return (
          <Route 
            {...rest}
            render={props => (<Redirect 
                                to={{
                                  pathname: '/admin/me'
                                }}
                                from="/"
                              />)}>
          </Route>
        );
      default:
        console.log("UserType not found");
    }
  }
  return (
    <Route {...rest}
                  render={(props) => <Login {...rest} />}></Route>
  );
}



  
export {PrivateRoute, UnauthenticatedRoute};