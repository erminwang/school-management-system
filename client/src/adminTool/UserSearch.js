import React, { Component } from 'react';
import { Input, Select, Checkbox, Button, Row, Col, message, Table, Pagination, InputNumber } from 'antd';
import { getUsersByUsernamesAndUserTypes } from '../util/APIUtils';

const TextArea = Input.TextArea;
const Option = Select.Option;
const CheckboxGroup = Checkbox.Group;

const allOptions = ["Student", "Instructor", "Admin"];
const defaultOptions = ["Student"];

const columns = [
    { title: 'id', width: 90, dataIndex: 'id', key: 'id', fixed: 'left' },
    { title: 'Username', dataIndex: 'username', key: 'username' },
    { title: 'First Name', dataIndex: 'firstName', key: 'firstName' },
    { title: 'Last Name', dataIndex: 'lastName', key: 'lastName' },
    { title: 'Gender', dataIndex: 'gender', key: 'gender' },
    { title: 'Email', dataIndex: 'email', key: 'email' },
    { title: 'User Type', dataIndex: 'userType', key: 'userType' },
    {
      title: 'Actions',
      key: 'actions',
      fixed: 'right',
      width: 80,
      render: (obj, record, index) => <a href="javascript:;" onClick={() => console.log(obj.id)}>action</a>,
    }
];

const UserSearchResult = ({props, handleSearch}) => {
    let users = props.userList.map((user) => {
        return {
            ...user,
            key: user.id.toString()
        };
    });

    if(!props.displayResult) {
        return (
            <div></div>
        );
    }
    return (
        <div>
            <h3>{props.totalElements} Result(s) Found</h3>
            <Pagination showQuickJumper current={props.page+1} pageSize={props.size} total={props.totalElements} onChange={(page, pageSize) => handleSearch(page-1)}/>
            <br />
            <Table pagination={false} columns={columns} dataSource={users} scroll={{ x: 600 }}/>
            <br />
            <Pagination showQuickJumper current={props.page+1} pageSize={props.size} total={props.totalElements} onChange={(page, pageSize) => handleSearch(page-1)}/>
            <br />
        </div>
    );
};

class UserSearch extends Component {

    constructor(props) {
        super(props);
        this.state = {
            checkedList: defaultOptions,
            indeterminate: true,
            checkAll: false,
            sortBy: "created",
            sortOrder: "desc",
            usernameText: "",
            displayResult: false,
            inputSize: 20,
            userList:[],
            page: 0,
            size: 0,
            totalElements: 0,
            last: false,
            totalPages: 0
        };
    }

    onUsersCheckAllChange = (e) => {
        this.setState({
            checkedList: e.target.checked ? allOptions : [],
            indeterminate: false,
            checkAll: e.target.checked,
        });
    }

    onSubusersCheckBoxChange = (checkedList) => {
        this.setState({
          checkedList,
          indeterminate: !!checkedList.length && (checkedList.length < allOptions.length),
          checkAll: checkedList.length === allOptions.length,
        });
    }

    handleSearch = (pageNum) => {
        console.log(this.state);
        if(!this.state.checkedList.length) {
            message.warning("Please at least choose a user type");
        } else {
            let userTypes = this.state.checkedList.join();
            let usernamesArr = (this.state.usernameText && this.state.usernameText.trim()) ? this.state.usernameText.split(";").map((name) => name.trim()) : [];
            
            let usernames = usernamesArr.join();
            getUsersByUsernamesAndUserTypes(userTypes, usernames, pageNum, this.state.inputSize, this.state.sortBy, this.state.sortOrder)
                .then((response) => {
                    this.setState({
                        displayResult: true,
                        userList: response.content,
                        page: response.page,
                        size: response.size,
                        totalPages: response.totalPages,
                        totalElements: response.totalElements,
                        last: response.last
                    });
                })
                .catch((e) => {
                    message.error("Fail to fetch: " + e);
                })
        }
    };

    render() {
        return (
            <div>
                <div style={{display: 'block'}}>
                    <Row>
                        <Col span={12}>
                            <div style={{ borderBottom: '1px solid #E9E9E9' }}>
                                <h3>User Types:</h3>
                                <Checkbox
                                    indeterminate={this.state.indeterminate}
                                    onChange={this.onUsersCheckAllChange}
                                    checked={this.state.checkAll}
                                >
                                    Check all
                                </Checkbox>
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={12}>
                            <CheckboxGroup options={allOptions} value={this.state.checkedList} onChange={this.onSubusersCheckBoxChange} />
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col span={12}>
                            <h3>Usernames (separate by ";")</h3>
                            <TextArea placeholder="Usernames separated by ';'" autosize={{ minRows: 2, maxRows: 6 }} onChange={(e) => this.setState({usernameText: e.target.value})}/>
                        </Col>
                    </Row>    
                    <br/>
                    <Row>
                        <Col span={6}>
                            <h3>Results Per Page: </h3>
                        </Col>
                        <Col span={6}>
                            <InputNumber size="large" min={10} max={50} defaultValue={20} onChange={(value) => this.setState({inputSize: value})}/>
                        </Col>
                    </Row>
                    <br />
                    <Row>
                        <Col span={12}>
                            <h3>Sort By: </h3>
                            <Select defaultValue={this.state.sortBy} style={{ width: 120, marginRight: 10}} onChange={(value) => this.setState({sortBy: value})}>
                                <Option value="created">Creation Date</Option>
                                <Option value="updated">Update Date</Option>
                                <Option value="username">Username</Option>
                                <Option value="id">User Id</Option>
                                <Option value="first">First Name</Option>
                                <Option value="last">Last Name</Option>
                            </Select>
                            <Select defaultValue={this.state.sortOrder} style={{ width: 120, marginRight: 10 }} onChange={(value) => this.setState({sortOrder: value})}>
                                <Option value="asc">Increasing</Option>
                                <Option value="desc">Decreasing</Option>
                            </Select>

                            <Button type="primary" icon="search" onClick={() => this.handleSearch(0)}>Search</Button>
                        </Col>    
                    </Row>
                    <br />
                </div>
                <UserSearchResult props={this.state} handleSearch={this.handleSearch} />
            </div>
        );
    }
}

export default UserSearch;