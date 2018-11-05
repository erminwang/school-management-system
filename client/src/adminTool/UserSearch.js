import React, { Component } from 'react';
import { Input, Select } from 'antd';

const InputGroup = Input.Group;
const Option = Select.Option;

class UserSearch extends Component {
    render() {
        return (
            <div style={{ marginTop: 50 }}>
                <InputGroup compact>
                    <Select defaultValue="Zhejiang">
                        <Option value="Zhejiang">Zhejiang</Option>
                        <Option value="Jiangsu">Jiangsu</Option>
                    </Select>
                    <Input style={{ width: '50%' }} defaultValue="Xihu District, Hangzhou" />
                </InputGroup>
            </div>
        );
    }
}

export default UserSearch;