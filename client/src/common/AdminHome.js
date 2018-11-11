import React, { Component } from 'react';
import { Layout, Menu, Icon } from 'antd';
import { withRouter } from 'react-router-dom';
import PollList from '../poll/PollList';
import Signup from '../user/signup/Signup';
import UserSearch from '../adminUI/UserSearch';
import Semester from '../adminUI/Semester';
import './AppSider.css';

const { Content, Sider } = Layout;
const SubMenu = Menu.SubMenu;

const COLLAPSED_WIDTH = "64";
const DEFAULT_WIDTH = 199;

class AdminHome extends Component {
    constructor(props) {
        super(props);
        this.state={
            menuIndex: "21",
            contentMarginLeft: DEFAULT_WIDTH,
            isRoot: false
        };
    }

    handleBreakPoint = (broken) => {
        if(broken) {
            this.setState({
                contentMarginLeft: Number.parseInt(COLLAPSED_WIDTH, 10)
            });
        } else {
            this.setState({
                contentMarginLeft: DEFAULT_WIDTH
            });
        }
    }

    rootRights = () => {
        if(this.state.isRoot) {
            return (
                <SubMenu className="sub-menu" key="sub5" title={<span><Icon type="robot" /><span>Root</span></span>}>
                    <Menu.Item key="51">Root Console</Menu.Item>
                    <Menu.Item key="52">User Deletion History</Menu.Item>
                </SubMenu>
            ) 
        }
    }

    render() {

        let contentBody = (
            <div>
                <h1>Welcome</h1>
            </div>
        );

        switch(this.state.menuIndex) {
            case "11":
                contentBody = (
                    <div>
                        <h1>Profile</h1>
                    </div>
                );
                break;
            case "21":
                contentBody = (
                    <Semester />
                );
                break;
            case "231":
                contentBody = (
                    <UserSearch />
                );
                break;
            case "232":
                contentBody = (
                    <Signup />
                );
                break;
            case "44":
                contentBody = (
                    <div style={{ padding: 24, background: '#fff', textAlign: 'center' }}>
                        ...
                        <br />
                        Really
                        <br />...<br />...<br />...<br />
                        long
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />...
                        <br />...<br />...<br />...<br />...<br />...<br />
                        content
                    </div>
                );
                break;
            case "42":
                contentBody = (<PollList 
                                isAuthenticated={this.props.isAuthenticated} 
                                currentUser={this.props.currentUser} 
                                handleLogout={this.props.handleLogout} 
                                {...this.props} />);
                break;
            default:
                console.log("Option not defined");
        }

        return (
            <div>
                <Sider 
                    style={{ overflow: 'auto', height: '100%', position: 'fixed', left: 0 }}
                    breakpoint="lg"
                    collapsedWidth={COLLAPSED_WIDTH}
                    onBreakpoint={(broken) => this.handleBreakPoint(broken)}
                >
                    <Menu
                        theme="light" 
                        mode="vertical" 
                        defaultSelectedKeys={[this.state.menuIndex]}
                        onSelect={({item, key, selectedKeys}) => this.setState({menuIndex: key})}
                    >
                        <SubMenu className="sub-menu" key="sub1" title={<span><Icon type="user" /><span>Profile</span></span>}>
                            <Menu.Item key="11">Personal Information</Menu.Item>
                            <Menu.Item key="12">Calendar</Menu.Item>
                            <Menu.Item key="13">Appointments</Menu.Item>
                            <Menu.Item key="14">Emergency Contact</Menu.Item>
                        </SubMenu>
                        {this.rootRights()}
                        <SubMenu className="sub-menu" key="sub2" title={<span><Icon type="cluster" /><span>Management</span></span>}>
                            <Menu.Item key="21">Semesters</Menu.Item>
                            <Menu.Item key="22">Departments</Menu.Item>
                            <SubMenu key="sub23" title={<span><Icon type="team" /><span>Users</span></span>}>
                                <Menu.Item key="231">Search Users</Menu.Item>
                                <Menu.Item key="232">Create Users</Menu.Item>
                            </SubMenu>
                            <Menu.Item key="24">Courses</Menu.Item>
                            <Menu.Item key="25">System Console</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub3" title={<span><Icon type="wallet" /><span>Payment</span></span>}>
                            <Menu.Item key="31">Payment History</Menu.Item>
                            <Menu.Item key="32">Payment FAQ</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub4" title={<span><Icon type="customer-service" /><span>Events</span></span>}>
                            <Menu.Item key="41">U-Pass BC</Menu.Item>
                            <Menu.Item key="42">Polls</Menu.Item>
                            <Menu.Item key="43">On-Campus Activities</Menu.Item>
                            <Menu.Item key="44">Off-Campus Activities</Menu.Item>
                        </SubMenu>
                        {/* <h6 style={{marginLeft:20, marginTop: 50}}>Ver. 2018</h6> */}
                    </Menu>
                </Sider>
                <Layout style={{ marginLeft: this.state.contentMarginLeft }}>
                    <Content className="app-content">
                        {contentBody}
                
                    </Content>
                </Layout>
            </div>    
        );
    }
}

export default withRouter(AdminHome);