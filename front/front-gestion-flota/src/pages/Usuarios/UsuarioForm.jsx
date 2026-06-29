import { useEffect, useState } from "react";
import "../Vehiculos/VehiculoForm.css";

function UsuarioForm({ onSubmit, usuario }) {

    const [form, setForm] = useState({
        username: "",
        password: "",
        rol: ""
    });

    useEffect(() => {

        if (usuario) {
            setForm({
                username: usuario.username,
                password: "",
                rol: usuario.rol
            });
        } else {
            setForm({
                username: "",
                password: "",
                rol: ""
            });
        }

    }, [usuario]);

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        onSubmit(form);

    };

    return (
        <form className="vehiculo-form" onSubmit={handleSubmit}>

            <input
                type="text"
                name="username"
                placeholder="Username"
                value={form.username}
                onChange={handleChange}
            />

            <input
                type="password"
                name="password"
                placeholder="Password"
                value={form.password}
                onChange={handleChange}
            />

            <select
                name="rol"
                value={form.rol}
                onChange={handleChange}
            >
                <option value="">Seleccione Rol</option>
                <option value="ADMIN">Admin</option>
                <option value="SUPERVISOR">Supervisor</option>
                <option value="OPERADOR">Operador</option>
            </select>

            <button type="submit">
                {usuario ? "Actualizar" : "Guardar"}
            </button>

        </form>
    );
}

export default UsuarioForm;