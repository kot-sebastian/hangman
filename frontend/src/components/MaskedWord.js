import React from 'react';

const MaskedWord = ({ maskedWord }) => {

  return (
    <div className="word">
      {maskedWord.split('').map((letter, i) => {
        return (
          <span className="letter" key={i}>
            {letter}
          </span>
        )
      })}
    </div>
  )
}

export default MaskedWord
