import React from 'react'

const Users = ({ users, activeUser }) => {

  return (
      <div className="users">
          <div>
              {users.length > 0 &&
                  <p>Users:</p>
              }
              {users
                  .map((user, i) => <span key={i} style={user === activeUser ? {fontWeight : "bold"} : {}}>{user}</span>)
                  .reduce((prev, curr) => prev === null ? [curr] : [prev, ', ', curr], null)}
          </div>
      </div>
  )
}

export default Users
