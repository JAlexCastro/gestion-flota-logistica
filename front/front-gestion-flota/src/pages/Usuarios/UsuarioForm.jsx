import { useEffect, useState } from "react";
import "../Vehiculos/VehiculoForm.css";

function UsuarioForm({ onSubmit, usuario }) {

    const [form, setForm] = useState({
        name: "",
        username: "",
        password: "",
        rol: ""
    });
    useEffect(() => {

        if (usuario) {
            setForm({
                name: usuario.name,
                username: usuario.username,
                password: "",
                rol: usuario.rol
            });
        } else {
            setForm({
                name: "",
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
                name="name"
                placeholder="Nombre Completo"
                value={form.name}
                onChange={handleChange}
                required
            />

            <input
                type="text"
                name="username"
                placeholder="Username"
                value={form.username}
                onChange={handleChange}
                required
            />

            <input
                type="password"
                name="password"
                placeholder={usuario ? "Nueva contraseña (opcional)" : "Password"}
                value={form.password}
                onChange={handleChange}
                required={!usuario}
            />

            <select
                name="rol"
                value={form.rol}
                onChange={handleChange}
                required
            >
                <option value="">Seleccione Rol</option>
                <option value="ADMIN">Administrador</option>
                <option value="CONDUCTOR">Conductor</option>
                <option value="OPERADOR">Operador</option>
            </select>

            <button type="submit">
                {usuario ? "Actualizar" : "Guardar"}
            </button>

        </form>
    );
}

export default UsuarioForm;