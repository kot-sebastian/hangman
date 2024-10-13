import React from 'react'

const UsedLetters = ({ usedLetters }) => {

  return (
    <div className="used-letters">
      <div>
        {usedLetters.length > 0 &&
          <p>Used letters:</p>
        }
        {usedLetters
          .map((letter, i) => <span key={i}>{letter}</span>)
          .reduce((prev, curr) => prev === null ? [curr] : [prev, ', ', curr], null)}
      </div>
    </div>
  )
}

export default UsedLetters
