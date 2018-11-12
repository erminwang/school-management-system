import React, { Component } from 'react';
import PollList from '../../poll/PollList';
import { getUserProfile } from '../../util/APIUtils';
import { Avatar, Tabs, Card, Row, Col } from 'antd';
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
        if(this.props.currentUser.username !== nextProps.currentUser.username) {
            this.loadUserProfile(nextProps.currentUser.username);
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
            <div>
                { 
                    this.state.user ? (
                        <div>
                            <Row>
                                <Col xs={24} sm={12} md={12} lg={12}>
                                    <div style={{textAlign: 'center'}}>
                                        <Avatar 
                                            size={100}
                                            style={{ fontSize: 30, backgroundColor: getAvatarColor(this.state.user.firstName + this.state.user.lastName)}}>
                                            {this.state.user.firstName[0].toUpperCase() + this.state.user.lastName[0].toUpperCase()}
                                        </Avatar>
                                        <div style={{marginTop: 20, marginBottom: 20}}>@{this.state.user.username}</div>
                                        <div>
                                            Joined SFU on {formatDate(this.state.user.joinedAt)}
                                        </div>
                                        <br />
                                    </div>
                                </Col>    
                                <Col xs={24} sm={12} md={12} lg={12}>
                                    <div>
                                        <div>
                                            <Card
                                                title="Basic Info"
                                            >
                                                <h5>First Name: {this.state.user.firstName}</h5>
                                                <h5>Last Name: {this.state.user.lastName}</h5>
                                                <h5>Email: {this.state.user.email}</h5>
                                                <h5>Gender: {this.state.user.gender}</h5>
                                                <h5>Primary Role: {this.state.user.primaryUserType}</h5>
                                                <h5>{this.state.user.secondaryUserType ? "Secondary Role: " + this.state.user.secondaryUserType : null}</h5>
                                            </Card>
                                        </div>
                                    </div>
                                </Col>
                            </Row>
                            <Row>
                                <Col span={24}>
                                    <div>    
                                        <Tabs defaultActiveKey="1" 
                                            animated={false}
                                            tabBarStyle={tabBarStyle}
                                            size="large"
                                        >
                                            <TabPane tab={`${this.state.user.pollCount} Polls`} key="1">
                                                <PollList username={this.props.currentUser.username} type="USER_CREATED_POLLS" />
                                            </TabPane>
                                            <TabPane tab={`${this.state.user.voteCount} Votes`}  key="2">
                                                <PollList username={this.props.currentUser.username} type="USER_VOTED_POLLS" />
                                            </TabPane>
                                        </Tabs>
                                    </div>
                                </Col>
                            </Row>   
                        </div>  
                    ): null               
                }
            </div>
        );
    }
}

export default Profile;