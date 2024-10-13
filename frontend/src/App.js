import React, {useEffect, useState} from 'react';
import Figure from './components/Figure';
import UsedLetters from './components/UsedLetters';
import Users from './components/Users';
import NotReadyUsers from './components/NotReadyUsers';
import MaskedWord from './components/MaskedWord';

import './App.css';

function App() {
    const localSelectedGameId = localStorage.getItem('selectedGameId') || "";
    const localSelectedUserId = localStorage.getItem('selectedUserId') || "";
    const [selectedGameId, setSelectedGameId] = useState(localSelectedGameId);
    const [selectedUserId, setSelectedUserId] = useState(localSelectedUserId);
    const [joinExisting, setJoinExisting] = useState(false);

    const [userId, setUserId] = useState(localSelectedUserId);
    const [gameId, setGameId] = useState(selectedGameId);

    const [playable, setPlayable] = useState(false);
    const [usedLetters, setUsedLetters] = useState([]);
    const [showNotification, setShowNotification] = useState(false);
    const [users, setUsers] = useState([]);
    const [activeUser, setActiveUser] = useState("");
    const [badLettersCount, setBadLettersCount] = useState(0);
    const [maskedWord, setMaskedWord] = useState('');
    const [readyUsers, setReadyUsers] = useState([]);

    const handleIAmReady = async () => {
        const response = await fetch(`http://localhost:8080/api/game/${gameId}/mark-user-ready?userId=${userId}`, {
            method: 'POST',
        });
        await response.json();
    };

    useEffect(() => {
        const handleKeydown = event => {
            const {key, keyCode} = event;
            if (playable && keyCode >= 65 && keyCode <= 90) {
                const letter = key.toUpperCase();
                fetch(
                    `http://localhost:8080/api/game/${gameId}/continue?userId=${userId}&letter=${letter}`, {method: 'POST'}
                )
                    .then((res) => res.json())
                    .then((data) => {
                        if (data.id !== "") {
                            setUsers(data.userIds);
                            setActiveUser(data.expectedMoveUserId);
                            setUsedLetters(data.usedLetters.split(''));
                            setBadLettersCount(data.badCounter);
                            setMaskedWord(data.maskedWord);
                            setReadyUsers(data.readyUserIds);
                            if (data.expectedMoveUserId === userId && data.status === "IN_PROGRESS") {
                                setPlayable(true);
                            } else {
                                setPlayable(false);
                            }
                        }
                    });
            }
        }
        window.addEventListener('keydown', handleKeydown);

        return () => window.removeEventListener('keydown', handleKeydown);
    }, [playable]);

    useEffect(() => {
        if (userId !== '' && gameId !== '') {
            fetch(
                `http://localhost:8080/api/game/state?gameId=${gameId}&userId=${userId}`
            )
                .then((res) => res.json())
                .then((data) => {
                    if (data.id !== "") {
                        setUsers(data.userIds);
                        setActiveUser(data.expectedMoveUserId);
                        setUsedLetters(data.usedLetters.split(''));
                        setBadLettersCount(data.badCounter);
                        setMaskedWord(data.maskedWord);
                        setReadyUsers(data.readyUserIds);
                        if (data.expectedMoveUserId === userId && data.status === "IN_PROGRESS") {
                            setPlayable(true);
                        } else {
                            setPlayable(false);
                        }
                    }
                });
        }
    }, [userId, gameId]);

    // periodically refresh // TODO async
    useEffect(() => {
        const intervalId = setInterval(fetchCurrentState, 2500);
        return () => clearInterval(intervalId);
    }, []);

    const fetchCurrentState = async () => {
        try {
            if (userId !== '' && gameId !== '') {
                const response = await fetch(`http://localhost:8080/api/game/state?gameId=${selectedGameId || gameId}&userId=${selectedUserId || userId}`);
                const data = await response.json();
                if (data.id !== "") {
                    setUsers(data.userIds);
                    setActiveUser(data.expectedMoveUserId);
                    setUsedLetters(data.usedLetters.split(''));
                    setBadLettersCount(data.badCounter);
                    setMaskedWord(data.maskedWord);
                    setReadyUsers(data.readyUserIds);
                    if (data.expectedMoveUserId === userId && data.status === "IN_PROGRESS") {
                        setPlayable(true);
                    } else {
                        setPlayable(false);
                    }
                }
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const handleNewGame = async () => {
        const response = await fetch(`http://localhost:8080/api/game?userId=${selectedUserId || userId}`, {
            method: 'POST',
        });
        let data = await response.json();
        if (data.id !== "") {
            setUsers(data.userIds);
            setActiveUser(data.expectedMoveUserId);
            setUsedLetters(data.usedLetters.split(''));
            setBadLettersCount(data.badCounter);
            setMaskedWord(data.maskedWord);
            setReadyUsers(data.readyUserIds);
            if (data.expectedMoveUserId === userId && data.status === "IN_PROGRESS") {
                setPlayable(true);
            } else {
                setPlayable(false);
            }
            setUserId(selectedUserId)
            setGameId(selectedGameId)
            localStorage.setItem("selectedGameId", data.id);
        }

    };

    const handleJoinGame = async () => {
        const response = await fetch(`http://localhost:8080/api/game/${selectedGameId || gameId}/join?userId=${selectedUserId || userId}`, {
            method: 'POST',
        });
        let data = await response.json();
        if (data.id !== "") {
            setUsers(data.userIds);
            setActiveUser(data.expectedMoveUserId);
            setUsedLetters(data.usedLetters.split(''));
            setBadLettersCount(data.badCounter);
            setMaskedWord(data.maskedWord);
            setReadyUsers(data.readyUserIds);
            if (data.expectedMoveUserId === userId && data.status === "IN_PROGRESS") {
                setPlayable(true);
            } else {
                setPlayable(false);
            }
            setUserId(selectedUserId)
            setGameId(selectedGameId)
        }
    };

    return (
        <>
            <h1>Hangman</h1>
            <p>Find the hidden word</p>

            <form>
                Username:
                <input
                    type="text"
                    placeholder="Username"
                    value={selectedUserId}
                    onChange={(e) => {
                        setSelectedUserId(e.target.value);
                        localStorage.setItem("selectedUserId", e.target.value);
                    }}
                    aria-label="username"
                />
            </form>

            <button onClick={(e) => {
                setJoinExisting(true);
            }}>Join existing game
            </button>

            <button onClick={handleNewGame}>Create new game</button>

            <form>
                Game id:
                <input
                    type="text"
                    placeholder="Game id"
                    value={selectedGameId || gameId}
                    onChange={(e) => {
                        setSelectedGameId(e.target.value);
                        localStorage.setItem("selectedGameId", e.target.value);
                    }}
                    aria-label="game-id"
                    disabled={!joinExisting}
                />
                {joinExisting
                    ? <button onClick={handleJoinGame}>Join</button>
                    : <></>
                }
            </form>

            {
                users.filter(x => !readyUsers.includes(x)).includes(userId)
                    ? <button onClick={handleIAmReady}>I am ready</button>
                    : <></>
            }

            <div className="game">
                <Figure badLettersCount={badLettersCount}/>
                <UsedLetters usedLetters={usedLetters}/>
                <MaskedWord maskedWord={maskedWord}/>
                <Users users={users} activeUser={activeUser}/>
                <NotReadyUsers users={users} readyUsers={readyUsers}/>
            </div>
        </>
    );
}

export default App;
