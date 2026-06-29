import { useEffect, useState } from "react";
import "./VehiculoForm.css";

function VehiculoForm({ onSubmit, vehiculo }) {

    const [form, setForm] = useState({
        patente: "",
        marca: "",
        modelo: "",
        anio: "",
        kilometrajeActual: "",
        estado: ""
    });

    // 🔥 cuando llega vehiculo (editar)
    useEffect(() => {

        if (vehiculo) {
            setForm(vehiculo);
        } else {
            setForm({
                patente: "",
                marca: "",
                modelo: "",
                anio: "",
                kilometrajeActual: "",
                estado: ""
            });
        }

    }, [vehiculo]);

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

            <input name="patente" value={form.patente} placeholder="Patente" onChange={handleChange} />
            <input name="marca" value={form.marca} placeholder="Marca" onChange={handleChange} />
            <input name="modelo" value={form.modelo} placeholder="Modelo" onChange={handleChange} />

            <input type="number" name="anio" value={form.anio} placeholder="Año" onChange={handleChange} />

            <input type="number" name="kilometrajeActual" value={form.kilometrajeActual} placeholder="Kilometraje" onChange={handleChange} />

            <input name="estado" value={form.estado} placeholder="Estado" onChange={handleChange} />

            <button type="submit">
                {vehiculo ? "Actualizar" : "Guardar"}
            </button>

        </form>
    );
}

export default VehiculoForm;