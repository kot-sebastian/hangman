import React from 'react'

const NotReadyUsers = ({users, readyUsers}) => {

    let notReadyUsers = users.filter(x => !readyUsers.includes(x));
    return (
        <div className="not-ready-users">
            <div>
                {notReadyUsers.length > 0 &&
                    <p>Not ready users:</p>
                }
                {notReadyUsers
                    .map((user, i) => <span key={i}>{user}</span>)
                    .reduce((prev, curr) => prev === null ? [curr] : [prev, ', ', curr], null)}
            </div>
        </div>
    )
}

export default NotReadyUsers
