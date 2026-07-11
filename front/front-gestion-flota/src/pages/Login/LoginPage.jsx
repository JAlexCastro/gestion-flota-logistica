import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { login } from "../../services/authService";

import "./Login.css";

function LoginPage() {

    const navigate = useNavigate();

    const [form, setForm] = useState({

        username: "",
        password: ""

    });

    const [error, setError] = useState("");

    const handleChange = (e) => {

        setForm({

            ...form,
            [e.target.name]: e.target.value

        });

    };

    const iniciarSesion = async (e) => {

    e.preventDefault();

    setError("");

    try {

        const response = await login(

            form.username,
            form.password
        );

        console.log(response);

        // Guarda el JWT
        localStorage.setItem(
            "token",
            response.token
        );

        // Guarda la información del usuario
        localStorage.setItem(
            "username",
            response.username
        );

        localStorage.setItem(
            "usuario",
            response.name
        );

        localStorage.setItem(
            "rol",
            response.rol
        );

        navigate("/");

    } catch (err) {

        setError("Usuario o contraseña incorrectos.");

    }

};

    return (

        <div className="login-container">

            <form
                className="login-card"
                onSubmit={iniciarSesion}
            >

                <h1>Gestión de Flota</h1>

                <p>

                    Iniciar Sesión

                </p>

                <input

                    type="text"

                    name="username"

                    placeholder="Usuario"

                    value={form.username}

                    onChange={handleChange}

                />

                <input

                    type="password"

                    name="password"

                    placeholder="Contraseña"

                    value={form.password}

                    onChange={handleChange}

                />

                {

                    error &&

                    <span className="login-error">

                        {error}

                    </span>

                }

                <button type="submit">

                    Ingresar

                </button>

            </form>

        </div>

    );

}

export default LoginPage;