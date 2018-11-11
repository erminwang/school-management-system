import React, { Component } from 'react';
import { Table, message } from 'antd';
import { getSemesters } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';

const columns = [{
    title: 'Semester',
    dataIndex: 'name',
    key: 'name',
    width: 150
  }, {
    title: 'Formal Date Span',
    children: [
        {
            title: 'Semester Start',
            dataIndex: 'startDate',
            key: 'startDate',
            width: 150
        },
        {
            title: 'Semester End',
            dataIndex: 'endDate',
            key: 'endDate',
            width: 150
        }
    ]
  }, {
    title: 'Enrollment Date Span',
    children: [
        {
            title: 'Enrollment Start',
            dataIndex: 'enrollmentStartDate',
            key: 'enrollmentStartDate',
            width: 150
        },
        {
            title: 'Enrollment End',
            dataIndex: 'enrollmentEndDate',
            key: 'enrollmentEndDate',
            width: 150
        }
    ]
}];

class Semester extends Component {
    constructor(props) {
        super(props);
        this.state = {
            semesterList: [],
            isLoading: true
        };
    }

    componentDidMount() {
        getSemesters()
            .then(response => {
                if(!response || !response.length) {
                    message.warning("Could not find any semester");
                } else {
                    this.setState({
                        semesterList: response,
                        isLoading: false
                    });
                }
            })
            .catch((e) => {
                message.error("Failed to fetch: " + e);
            });
    }

    render() {

        if(this.state.isLoading) {
            return (
                <LoadingIndicator />
            )
        }
        let semesterData = this.state.semesterList.map((semester) => {
            return {
                ...semester,
                key: semester.id
            }
        })

        return (
            <div>
                <h1>Manage Semesters</h1>
                <Table
                    columns={columns}
                    dataSource={semesterData}
                    bordered
                    pagination={false}
                    // scroll={{ x: '130%', y: 240 }}
                />
            </div>
        );
    }
}

export default Semester;