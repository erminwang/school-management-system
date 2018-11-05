import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN, USER_TYPE,
          STUDENT, INSTRUCTOR, ADMIN } from '../constants';

import AppSider from '../common/AppSider';
import InstructorHome from '../common/InstructorHome';
import AdminHome from '../common/AdminHome';
import Faq from "../util/Faq";
import NewPoll from '../poll/NewPoll';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import { PrivateRoute, UnauthenticatedRoute } from '../common/CustomizedRoute';

import { Layout, notification } from 'antd';

const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      userType: ""
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });    
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
    .then(response => {
      const userType = response.userType;
      console.log(userType);
      this.setState({
        userType: userType,
        currentUser: response,
        isAuthenticated: true,
        isLoading: false
      });
      if(userType === STUDENT) {
        this.props.history.push("/student/me");
      } else if(userType === INSTRUCTOR) {
        this.props.history.push("/instructor/me");
      } else if (userType === ADMIN) {
        this.props.history.push("/admin/me")
      }
    }).catch(error => {
      this.setState({
        isLoading: false
      });
    });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    
    notification[notificationType]({
      message: 'College Board',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'College Board',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
  }

  render() {
    if(this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
        <Layout className="app-container">
          <AppHeader isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout} />
          
          <Content className="app-content">
            <div className="container">
              <Switch>
                        
                <PrivateRoute isAuthenticated={this.state.isAuthenticated} path="/poll/new" component={NewPoll} handleLogout={this.handleLogout}></PrivateRoute>
                
                <Route exact path="/FAQ" component={Faq}></Route>

                <PrivateRoute path="/users/:username" 
                  component={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                  </PrivateRoute>

                <PrivateRoute 
                  isAuthenticated={this.state.isAuthenticated} 
                  path="/student/me" component={AppSider}
                  handleLogout={this.handleLogout}
                  currentUser={this.state.currentUser}>
                </PrivateRoute>

                <PrivateRoute 
                  isAuthenticated={this.state.isAuthenticated} 
                  path="/instructor/me" component={InstructorHome}
                  handleLogout={this.handleLogout}
                  currentUser={this.state.currentUser}>
                </PrivateRoute>

                <PrivateRoute 
                  isAuthenticated={this.state.isAuthenticated} 
                  path="/admin/me" component={AdminHome}
                  handleLogout={this.handleLogout}
                  currentUser={this.state.currentUser}>
                </PrivateRoute>

                <UnauthenticatedRoute exact path="/"
                    isAuthenticated = {this.state.isAuthenticated}
                    onLogin={this.handleLogin}
                    handleLogout={this.handleLogout}
                    userType={this.state.userType}
                    {...this.props} >
                </UnauthenticatedRoute>
                      
                <Route component={NotFound}></Route>
                  {/* <Route exact path="/" render={(props) => <PollList isAuthenticated={this.state.isAuthenticated} 
                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>}>
                  </Route>       */}
                  {/* <Route exact path="/polls" 
                  render={(props) => <PollList isAuthenticated={this.state.isAuthenticated} 
                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                  </Route> */}          
              </Switch>
            </div>
          </Content>  
        </Layout>
    );
  }
}

export default withRouter(App);
