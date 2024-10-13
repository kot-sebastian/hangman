import React from 'react'

const Figure = ({ badLettersCount }) => {
  return (
      <svg height="300" width="250" className="figure">
          <line x1="60" y1="20" x2="160" y2="20"/>
          <line x1="160" y1="20" x2="160" y2="50"/>
          <line x1="60" y1="40" x2="90" y2="20"/>
          <line x1="60" y1="20" x2="60" y2="260"/>
          <line x1="0" y1="260" x2="120" y2="260"/>
          <line x1="10" y1="260" x2="60" y2="220"/>
          <line x1="10" y1="260" x2="60" y2="220"/>
          <line x1="60" y1="220" x2="110" y2="260"/>

          {badLettersCount > 0 &&
              <circle cx="160" cy="70" r="20"/>
          }
          {badLettersCount > 1 &&
              <line x1="160" y1="90" x2="160" y2="150"/>
          }
          {badLettersCount > 2 &&
              <line x1="160" y1="120" x2="140" y2="100"/>
          }
          {badLettersCount > 3 &&
              <line x1="160" y1="120" x2="180" y2="100"/>
          }
          {badLettersCount > 4 &&
              <line x1="160" y1="150" x2="140" y2="180"/>
          }
          {badLettersCount > 5 &&
              <line x1="160" y1="150" x2="180" y2="180"/>
          }
      </svg>
  )
}

export default Figure
