import React, { Component } from 'react';
import PollList from '../../poll/PollList';
import { getUserProfile } from '../../util/APIUtils';
import { Avatar, Tabs, Card } from 'antd';
import { getAvatarColor } from '../../util/Colors';
import { formatDate } from '../../util/Helpers';
import LoadingIndicator  from '../../common/LoadingIndicator';
import './Profile.css';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';

const TabPane = Tabs.TabPane;

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            isLoading: false
        }
        this.loadUserProfile = this.loadUserProfile.bind(this);
    }

    loadUserProfile(username) {
        this.setState({
            isLoading: true
        });

        getUserProfile(username)
        .then(response => {
            this.setState({
                user: response,
                isLoading: false
            });
        }).catch(error => {
            if(error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });        
            }
        });        
    }
      
    componentDidMount() {
        const username = this.props.currentUser.username;
        this.loadUserProfile(username);
    }

    componentDidUpdate(nextProps) {
        if(this.props.match.params.username !== nextProps.match.params.username) {
            this.loadUserProfile(nextProps.match.params.username);
        }        
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        return (
            <div className="profile">
                { 
                    this.state.user ? (
                        <div className="user-profile">
                            <h2></h2>
                            <div className="user-details">
                                <div className="user-avatar">
                                    <Avatar className="user-avatar-circle" style={{ backgroundColor: getAvatarColor(this.state.user.firstName + this.state.user.lastName)}}>
                                        {this.state.user.firstName[0].toUpperCase() + this.state.user.lastName[0].toUpperCase()}
                                    </Avatar>
                                </div>
                                <div className="user-summary">
                                    <div className="full-name">{this.state.user.name}</div>
                                    <div className="username">@{this.state.user.username}</div>
                                    <div className="user-joined">
                                        Joined SFU on {formatDate(this.state.user.joinedAt)}
                                    </div>
                                    <br />
                                    <div>
                                        <Card
                                            title="Basic Info"
                                        >
                                            <h5>First Name: {this.state.user.firstName}</h5>
                                            <h5>Last Name: {this.state.user.lastName}</h5>
                                            <h5>Email: {this.state.user.email}</h5>
                                            <h5>Gender: {this.state.user.gender}</h5>
                                            <h5>Primary Role: {this.state.user.userType}</h5>
                                            <h5>{this.state.user.secondaryUserType ? "Secondary Role: " + this.state.user.secondaryUserType : null}</h5>
                                        </Card>
                                    </div>
                                </div>
                            </div>
                            <div className="user-poll-details">    
                                <Tabs defaultActiveKey="1" 
                                    animated={false}
                                    tabBarStyle={tabBarStyle}
                                    size="large"
                                    className="profile-tabs">
                                    <TabPane tab={`${this.state.user.pollCount} Polls`} key="1">
                                        <PollList username={this.props.match.params.username} type="USER_CREATED_POLLS" />
                                    </TabPane>
                                    <TabPane tab={`${this.state.user.voteCount} Votes`}  key="2">
                                        <PollList username={this.props.match.params.username} type="USER_VOTED_POLLS" />
                                    </TabPane>
                                </Tabs>
                            </div>  
                        </div>  
                    ): null               
                }
            </div>
        );
    }
}

export default Profile;