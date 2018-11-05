import React, { Component } from 'react';
import { Layout, Menu, Icon } from 'antd';
import { withRouter } from 'react-router-dom';
import PollList from '../poll/PollList';
import './AppSider.css';

const { Content, Sider } = Layout;
const SubMenu = Menu.SubMenu;

const COLLAPSED_WIDTH = "64";
const DEFAULT_WIDTH = 199;

class AppSider extends Component {
    constructor(props) {
        super(props);
        this.state={
            menuIndex: "11",
            contentMarginLeft: DEFAULT_WIDTH
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
            case "45":
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
            case "52":
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
                        <SubMenu className="sub-menu" key="sub2" title={<span><Icon type="solution" /><span>Academics</span></span>}>
                            <Menu.Item key="21">Search</Menu.Item>
                            <Menu.Item key="22">Course Schedule</Menu.Item>
                            <Menu.Item key="23">Academic Progress</Menu.Item>
                            <Menu.Item key="24">Grades</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub3" title={<span><Icon type="skin" /><span>Recreation</span></span>}>
                            <Menu.Item key="31">Athletics Waiver</Menu.Item>
                            <Menu.Item key="32">Waiver FAQ</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub4" title={<span><Icon type="wallet" /><span>Finances</span></span>}>
                            <Menu.Item key="41">Account Inquiry</Menu.Item>
                            <Menu.Item key="42">T4A Data</Menu.Item>
                            <Menu.Item key="43">T2202A Data</Menu.Item>
                            <Menu.Item key="44">Scholarships</Menu.Item>
                            <Menu.Item key="45">Financial Aid and Rewards</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub5" title={<span><Icon type="customer-service" /><span>Events</span></span>}>
                            <Menu.Item key="51">U-Pass BC</Menu.Item>
                            <Menu.Item key="52">Polls</Menu.Item>
                            <Menu.Item key="53">On-Campus Activities</Menu.Item>
                            <Menu.Item key="54">Off-Campus Activities</Menu.Item>
                        </SubMenu>
                        <SubMenu className="sub-menu" key="sub6" title={<span><Icon type="bell" /><span>Admissions</span></span>}>
                            <Menu.Item key="61">Application Status</Menu.Item>
                            <Menu.Item key="62">Program Change</Menu.Item>
                            <Menu.Item key="63">Apply for Graduate Studies</Menu.Item>
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

export default withRouter(AppSider);